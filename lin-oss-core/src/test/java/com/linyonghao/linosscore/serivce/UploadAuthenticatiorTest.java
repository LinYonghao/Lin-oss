package com.linyonghao.linosscore.serivce;

import com.linyonghao.linosscore.model.UploadPolicy;
import com.linyonghao.linosscore.model.UserModel;
import com.linyonghao.linosscore.service.certificate.NotUploadKeyException;
import com.linyonghao.linosscore.service.certificate.UploadAuthenticatorImpl;
import org.junit.jupiter.api.Test;

public class UploadAuthenticatiorTest {
    @Test
    public void testJSON(){

        UserModel userModel = new UserModel();
        userModel.setAccessKey("123");
        userModel.setSecretKey("321");

        UploadAuthenticatorImpl uploadAuthenticator = new UploadAuthenticatorImpl();
        UploadPolicy uploadPolicy = new UploadPolicy();
        uploadPolicy.setMINE("*");
//        uploadPolicy.setKey("abc.png");
        try {
            uploadAuthenticator.generateCertification(userModel,uploadPolicy);
        } catch (NotUploadKeyException e) {
            e.printStackTrace();
        }
    }
}
