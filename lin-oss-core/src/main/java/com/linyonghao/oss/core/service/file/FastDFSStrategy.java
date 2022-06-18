package com.linyonghao.oss.core.service.file;

import com.linyonghao.oss.common.entity.OSSFile;
import com.linyonghao.oss.common.utils.EnvUtil;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

public class FastDFSStrategy implements FileStrategy {
    private static final Logger logger = LoggerFactory.getLogger(FastDFSStrategy.class);

    static {

        try {

            Properties properties = new Properties();
            properties.setProperty("connect_timeout","60");
            properties.setProperty("network_timeout","60");
            properties.setProperty("charset","UTF-8");
            properties.setProperty("fastdfs.tracker_servers", EnvUtil.getEnv("LIN_DFS_TRACKER_HOST","127.0.0.1") + ":22122" );
            ClientGlobal.initByProperties(properties);

        } catch (IOException | MyException e) {
            e.printStackTrace();
            logger.error("初始化 FastDFS 错误");
        }

    }

    public String uploadFile(OSSFile file) throws FileUploadException {

        NameValuePair[] metaList = new NameValuePair[1];
        metaList[0] = new NameValuePair(file.getAuthor());


        try {
            StorageClient trackerClient = getTrackerClient();
            String[] result;
            if (file.getBin() != null) {
                result = trackerClient.upload_file(file.getBin(), file.getExt(), metaList);
            } else {
                result = trackerClient.upload_file(file.getAbsolutePath(), file.getExt(), metaList);
            }


            if (result == null) {
                logger.error("上传文件错误，错误码: " + trackerClient.getErrorCode());
                throw new FileUploadException("上传失败");
            }

            return result[0] + "-" + result[1];
        } catch (IOException | MyException e) {
            e.printStackTrace();
            logger.error("上传文件失败" + e.getMessage());
            throw new FileUploadException(e.getMessage());
        }

    }

    @Override
    public byte[] downloadFile(String fileKey) throws FileDownloadException {
        String[] split = fileKey.split("-");
        if (split.length != 2){
            throw new IllegalArgumentException("不符合FastDFS的fileKey");
        }
        StorageClient trackerClient = getTrackerClient();
        try {
            return trackerClient.download_file(split[0], split[1]);
        } catch (IOException |MyException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new FileDownloadException("下载失败");
        }
    }


    public StorageClient getTrackerClient() {
        TrackerServer trackerServer = getTrackerServer();
        return new StorageClient(trackerServer, null);
    }


    public TrackerServer getTrackerServer() {
        TrackerClient trackerClient = new TrackerClient();
        try {
            return trackerClient.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取Tracker失败");
        }
        return null;

    }


}
