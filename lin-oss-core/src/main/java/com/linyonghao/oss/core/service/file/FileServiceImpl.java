package com.linyonghao.oss.core.service.file;

import com.linyonghao.oss.common.dao.mapper.relationship.ObjectMapper;
import com.linyonghao.oss.common.model.ObjectBucketDO;
import com.linyonghao.oss.common.model.UploadPolicy;
import com.linyonghao.oss.common.model.UserModel;
import com.linyonghao.oss.core.constant.MQName;
import com.linyonghao.oss.core.dto.DownloadMessage;
import com.linyonghao.oss.core.dto.UploadMessage;
import com.linyonghao.oss.core.entity.DownloadParams;
import com.linyonghao.oss.common.entity.OSSFile;
import com.linyonghao.oss.core.util.TimeUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    FileStrategy fileStrategy;

    public FileServiceImpl(FileStrategy fileStrategy) {
        this.fileStrategy = fileStrategy;
    }

    @Override
    public String uploadFile(OSSFile file, UploadPolicy uploadPolicy, UserModel userModel) throws FileUploadException {

        String ret = fileStrategy.uploadFile(file);
        // 回调处理
        file.setRemoteFilename(ret);
        if (uploadPolicy.getCallback() != null && !uploadPolicy.getCallback().isEmpty()) {
            rabbitTemplate.convertAndSend(MQName.TOPIC_EXCHANGE_NAME, MQName.UPLOAD_CALLBACK, new UploadMessage(file, uploadPolicy, userModel, new Date()));
        }

        rabbitTemplate.convertAndSend(MQName.TOPIC_EXCHANGE_NAME, MQName.UPLOAD_LOG, new UploadMessage(file, uploadPolicy, userModel, new Date()));
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
                        new Date().getTime()
                ));
        ossFile.setBin(bin);
        return ossFile;
    }
}
