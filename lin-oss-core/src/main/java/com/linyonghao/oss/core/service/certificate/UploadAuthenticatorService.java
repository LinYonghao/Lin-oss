package com.linyonghao.oss.core.service.certificate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linyonghao.oss.common.model.UploadPolicy;
import com.linyonghao.oss.common.model.UserModel;
import com.linyonghao.oss.common.service.UserService;
import com.linyonghao.oss.common.service.cache.UserCacheService;
import com.linyonghao.oss.core.dto.UploadCertificationDTO;
import com.linyonghao.oss.common.dao.mapper.relationship.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;


/**
 * 上传凭证
 * 参考七牛云算法 <a href="https://developer.qiniu.com/kodo/1208/upload-token"/>
 * 1.先对上传政策进行JSON后转base64
 * 2.拼接①-SecretKey进行SHA1签名
 * 3.对签名②进行Base64
 * 4.拼接  AccessKey + ':' + encodedSign + ':' + encodedPutPolicy
 */
@Service
public class UploadAuthenticatorService {
    Logger logger = LoggerFactory.getLogger(UploadAuthenticatorService.class);


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    /**
     * 生成上传凭证
     * @param userModel
     * @param uploadPolicy
     * @return 凭证
     */
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

    /**
     *
     * @param key
     * @return
     * @throws NotKeyException token不存在异常
     * @throws KeyNumberException token分割数量异常
     * @throws EmptyUploadPolicy 空上传策略
     * @throws NotfoundUserModelException 没有该该用户
     * @throws AuthenticationNotMatch 凭证不匹配
     */
    public UploadCertificationDTO decryptCertification(String key) throws NotKeyException, KeyNumberException, EmptyUploadPolicy, NotfoundUserModelException, AuthenticationNotMatch {
        if(key == null || key.isEmpty()){
            throw new NotKeyException("无法获取上传token");
        }

        String[] keySplit = key.split("@");

        if(keySplit.length != 3){
            throw new KeyNumberException("凭证格式错误");
        }

        String accessKey = keySplit[0];
        String encodedSign = keySplit[1];
        String encodedPolicy = keySplit[2];
        // json -> base64

        byte[] decodePolicy = Base64.getDecoder().decode(encodedPolicy);
        UploadPolicy uploadPolicy = JSON.parseObject(decodePolicy, UploadPolicy.class);

        if(uploadPolicy.getKey() == null ){
            throw new EmptyUploadPolicy("上传策略获取失败");
        }

        // 查缓存
        UserModel userModel = userService.getByAccessKey(accessKey);

        // 校验
        String withAccessKey = String.format("%s-%s", encodedPolicy, userModel.getSecretKey());
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            sha1.update(withAccessKey.getBytes(StandardCharsets.UTF_8));
            byte[] sign = Base64.getEncoder().encode(sha1.digest());
            if(!new String(sign, StandardCharsets.UTF_8).equals(encodedSign)){
                throw new AuthenticationNotMatch("凭证不匹配");
            }

        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            return null;
        }

        return new UploadCertificationDTO(userModel,uploadPolicy);
    }
}
