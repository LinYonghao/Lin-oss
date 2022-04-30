package com.linyonghao.linosscore.serivce;

import com.linyonghao.linosscore.service.file.FileDownloadException;
import com.linyonghao.linosscore.service.file.FileService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DownloadFileTest {
    Logger logger = LoggerFactory.getLogger(DownloadFileTest.class);
    @Autowired
    FileService fileService;
    @Test
    public void download(){
        String groupName = "group1";
        String remoteName = "M00/00/00/rBRgLmJrSFuAFyKoAAAvjrRxLiE969.png";
        try {
            byte[] bytes = fileService.downloadFile(groupName + '-' + remoteName, null);
            logger.info(String.valueOf(bytes.length));
        } catch (FileDownloadException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }


    }
}
