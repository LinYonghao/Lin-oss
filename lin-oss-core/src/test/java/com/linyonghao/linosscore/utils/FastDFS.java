package com.linyonghao.linosscore.utils;

import com.linyonghao.linosscore.entity.FastDFSFile;
import com.linyonghao.linosscore.exception.FastDFSUploadException;
import com.linyonghao.linosscore.util.FastDFSUtil;
import org.junit.jupiter.api.Test;

import java.io.*;

public class FastDFS {

    @Test
    public void upload(){
        FastDFSFile fastDFSFile = new FastDFSFile();
        fastDFSFile.setAbsolutePath("d:/何同学采访库克.docx");
        fastDFSFile.setExt("docx");
        fastDFSFile.setAuthor("Lin Yonghao");
        try {
            FastDFSUtil.uploadFile(fastDFSFile);
        } catch (FastDFSUploadException e) {
            e.printStackTrace();
        }

    }
}
