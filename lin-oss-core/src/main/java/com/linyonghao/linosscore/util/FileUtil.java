package com.linyonghao.linosscore.util;
import java.net.*;

public class FileUtil{
    public static String getMimeType(String filename)
            throws java.io.IOException, MalformedURLException
    {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        return fileNameMap.getContentTypeFor(filename);
    }

    public static String getTempDir(){
        return System.getProperty("java.io.tmpdir");
    }

    public static String getExt(String filename){
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

}
