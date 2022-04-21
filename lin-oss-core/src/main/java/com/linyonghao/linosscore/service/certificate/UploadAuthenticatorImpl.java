package com.linyonghao.linosscore.service.certificate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.linyonghao.linosscore.model.UploadPolicy;
import com.linyonghao.linosscore.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.NotActiveException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


/**
 * 上传凭证
 * 参考七牛云算法 <a href="https://developer.qiniu.com/kodo/1208/upload-token"/>
 * 1.先对上传政策进行JSON后转base64
 * 2.拼接①-SecretKey进行SHA1签名
 * 3.对签名②进行Base64
 * 4.拼接  AccessKey + ':' + encodedSign + ':' + encodedPutPolicy
 */
public class UploadAuthenticatorImpl implements UploadAuthenticator {
    Logger logger = LoggerFactory.getLogger(UploadAuthenticatorImpl.class);

    @Override
    public String generateCertification(UserModel userModel, UploadPolicy uploadPolicy) {
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.setPropertyNamingStrategy(PropertyNamingStrategy.PascalCase);
        String policyString = JSON.toJSONString(uploadPolicy, serializeConfig);
        byte[] policyBase64Byte = Base64.getEncoder().encode(policyString.getBytes(StandardCharsets.UTF_8));

        String policyBase64 = new String(policyBase64Byte, StandardCharsets.UTF_8);

        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            String withAccessKey = String.format("%s-%s", policyBase64, userModel.getSecretKey());
            sha1.update(withAccessKey.getBytes(StandardCharsets.UTF_8));
            byte[] digest = sha1.digest();
            byte[] sign = Base64.getEncoder().encode(digest);


            String result = userModel.getAccessKey() +
                    "@" +
                    new String(sign, StandardCharsets.UTF_8) +
                    "@" +
                    policyBase64;


            logger.debug(result);
            return result;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        logger.debug("上传凭证无法获取");
        return null;
    }

    @Override
    public UserModel decryptCertification(String key) throws NotKeyException, KeyNumberException {
        if(key == null || key.isEmpty()){
            throw new NotKeyException("无法获取上传token");
        }

        String[] keySplit = key.split("@");

        if(keySplit.length != 3){
            throw new KeyNumberException();
        }


        return null;
    }
}
