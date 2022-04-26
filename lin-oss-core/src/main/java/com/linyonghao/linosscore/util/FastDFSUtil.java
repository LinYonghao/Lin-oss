package com.linyonghao.linosscore.util;

import com.linyonghao.linosscore.entity.FastDFSFile;
import com.linyonghao.linosscore.exception.FastDFSUploadException;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class FastDFSUtil {
    private static final Logger logger = LoggerFactory.getLogger(FastDFSUtil.class);

    static {

        try {
            String absolutePath = new ClassPathResource("fastdfs-client.conf").getFile().getAbsolutePath();
            ClientGlobal.init(absolutePath);

        } catch (IOException | MyException e) {
            e.printStackTrace();
            logger.error("初始化 FastDFS 错误");
        }

    }

    public static String[] uploadFile(FastDFSFile file) throws FastDFSUploadException {


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
            }

            return result;
        } catch (IOException | MyException e) {
            e.printStackTrace();
            logger.error("上传文件失败" + e.getMessage());
            throw new FastDFSUploadException(e.getMessage());
        }

    }


    public static StorageClient getTrackerClient() {
        TrackerServer trackerServer = getTrackerServer();
        return new StorageClient(trackerServer, null);
    }


    public static TrackerServer getTrackerServer() {
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
