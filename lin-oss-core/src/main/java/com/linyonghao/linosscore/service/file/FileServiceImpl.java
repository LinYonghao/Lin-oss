package com.linyonghao.linosscore.service.file;

import com.linyonghao.linosscore.constant.MQName;
import com.linyonghao.linosscore.dto.UploadMessage;
import com.linyonghao.linosscore.entity.DownloadParams;
import com.linyonghao.linosscore.entity.OSSFile;
import com.linyonghao.linosscore.model.UploadPolicy;
import com.linyonghao.linosscore.model.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    FileStrategy fileStrategy;

    public FileServiceImpl(FileStrategy fileStrategy) {
        this.fileStrategy = fileStrategy;
    }

    @Override
    public String uploadFile(OSSFile file, UploadPolicy uploadPolicy, UserModel userModel) throws FileUploadException {

        String ret = fileStrategy.uploadFile(file);
        // 回调处理

        if(uploadPolicy.getCallback() != null && !uploadPolicy.getCallback().isEmpty()){
            rabbitTemplate.convertAndSend(MQName.TOPIC_EXCHANGE_NAME,MQName.UPLOAD_CALLBACK,new UploadMessage(file,uploadPolicy));
        }

        rabbitTemplate.convertAndSend(MQName.TOPIC_EXCHANGE_NAME,MQName.UPLOAD_LOG,new UploadMessage(file,uploadPolicy));
        return ret;
    }

    @Override
    public byte[] downloadFile(String fileKey, DownloadParams downloadParams) throws FileDownloadException {
        return fileStrategy.downloadFile(fileKey);
    }
}
