package com.linyonghao.oss.core.serivce;

import com.linyonghao.oss.common.entity.OSSFile;
import com.linyonghao.oss.core.service.file.FileDownloadException;
import com.linyonghao.oss.core.service.file.FileService;
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
}
