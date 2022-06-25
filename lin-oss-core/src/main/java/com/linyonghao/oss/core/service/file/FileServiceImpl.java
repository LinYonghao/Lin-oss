package com.linyonghao.oss.core.service.file;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linyonghao.oss.common.dao.mapper.relationship.ObjectMapper;
import com.linyonghao.oss.common.entity.CoreBucket;
import com.linyonghao.oss.common.entity.CoreObject;
import com.linyonghao.oss.common.model.ObjectBucketDO;
import com.linyonghao.oss.common.model.UploadPolicy;
import com.linyonghao.oss.common.model.UserModel;
import com.linyonghao.oss.common.service.ICoreBucketService;
import com.linyonghao.oss.common.service.ICoreObjectService;
import com.linyonghao.oss.core.constant.MQName;
import com.linyonghao.oss.common.dto.DownloadMessage;
import com.linyonghao.oss.core.dto.UploadMessage;
import com.linyonghao.oss.common.entity.DownloadParams;
import com.linyonghao.oss.common.entity.OSSFile;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ICoreBucketService coreBucketService;

    @Autowired
    private ICoreObjectService coreObjectService;

    FileStrategy fileStrategy;

    public FileServiceImpl(FileStrategy fileStrategy) {
        this.fileStrategy = fileStrategy;
    }

    @Override
    public String uploadFile(OSSFile file, UploadPolicy uploadPolicy, UserModel userModel) throws FileUploadException, NotFoundBucketException {
        String ret = fileStrategy.uploadFile(file);
        // 回调处理
        file.setRemoteFilename(ret);
        // 写库
        String scope = uploadPolicy.getScope();
        QueryWrapper<CoreBucket> wrapper = new QueryWrapper<>();
        wrapper.eq("name", scope);
        wrapper.eq("user_id", userModel.getId());
        CoreBucket bucket = coreBucketService.getOne(wrapper);
        if (bucket == null){
            throw new NotFoundBucketException();
        }
        CoreObject coreObject = new CoreObject();
        coreObject.setBucketId(bucket.getId());
        coreObject.setRemoteKey(uploadPolicy.getKey());
        // 若无mine自动获取
        if(uploadPolicy.getMINE() == null){
            try {
                String minetype = Files.probeContentType(new File(file.getAbsolutePath()).toPath());
                uploadPolicy.setMINE(minetype);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        coreObject.setMine(uploadPolicy.getMINE());
        coreObject.setLocalKey(ret);
        coreObject.setCreateTime(new Date());
        coreObject.setSize(file.getSize());
        coreObject.setBucketId(bucket.getId());
        coreObjectService.save(coreObject);

        if (uploadPolicy.getCallback() != null && !uploadPolicy.getCallback().isEmpty()) {
            rabbitTemplate.convertAndSend(MQName.TOPIC_EXCHANGE_NAME, MQName.UPLOAD_CALLBACK,
                    new UploadMessage(file, uploadPolicy, userModel, new Date(),bucket.getId()));
        }

        rabbitTemplate.convertAndSend(MQName.TOPIC_EXCHANGE_NAME, MQName.UPLOAD_LOG,
                new UploadMessage(file, uploadPolicy, userModel, new Date(),bucket.getId()));
        return ret;
    }

    @Override
    public OSSFile downloadFile(long bucketId, String key, DownloadParams downloadParams, String clientIp) throws FileDownloadException {
        ObjectBucketDO objectBucketDO = objectMapper.selectObjectByPath(bucketId, key);
        OSSFile ossFile = new OSSFile();
        ossFile.setMine(objectBucketDO.getObjectModel().getMine());
        byte[] bin = fileStrategy.downloadFile(objectBucketDO.getObjectModel().getLocalKey());
        ossFile.setSize(bin.length);
        // 发送 MQ 记录
        rabbitTemplate.convertAndSend(
                MQName.DOWNLOAD_LOG,
                new DownloadMessage(
                        downloadParams,
                        key,
                        bin.length,
                        clientIp,
                        objectBucketDO.getBucketModel().getUserId(),
                        new Date().getTime(),
                        bucketId
                ));
        ossFile.setBin(bin);
        return ossFile;
    }
}
