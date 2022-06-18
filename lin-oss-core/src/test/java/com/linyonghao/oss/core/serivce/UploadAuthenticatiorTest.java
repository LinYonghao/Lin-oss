package com.linyonghao.oss.core.serivce;

import com.linyonghao.oss.common.model.UploadPolicy;
import com.linyonghao.oss.common.model.UserModel;
import com.linyonghao.oss.core.dto.UploadCertificationDTO;
import com.linyonghao.oss.core.service.certificate.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UploadAuthenticatiorTest {

    @Autowired
    private UploadAuthenticator uploadAuthenticator;

    @Test
    public void generateUploadPolicy(){

        UserModel userModel = new UserModel();
        userModel.setAccessKey("de89998a-c2b6-40e0-827d-6a23d86efbd0");
        userModel.setSecretKey("a22d16120ecd4c0cae4b294003c3befe");

        UploadPolicy uploadPolicy = new UploadPolicy();
        uploadPolicy.setMINE("image/png");
        uploadPolicy.setKey("abc.png");
        uploadPolicy.setScope("bucket1");
        System.out.println(uploadAuthenticator.generateCertification(userModel, uploadPolicy));

    }

    @Test
    public void decryptCertification(){
        String token = "123@3GfTMnLwkTsKOif6PIySIWc+DA0=@eyJEZWFkbGluZSI6MCwiS2V5IjoiYWJjLnBuZyIsIk1JTkUiOiIqIn0=";
        UserModel userModel = new UserModel();
        UploadPolicy uploadPolicy = new UploadPolicy();
        try {
            UploadCertificationDTO uploadCertificationDTO = uploadAuthenticator.decryptCertification(token);
            uploadCertificationDTO = uploadAuthenticator.decryptCertification(token);


        } catch (NotKeyException e) {
            e.printStackTrace();
        } catch (KeyNumberException e) {
            e.printStackTrace();
        } catch (EmptyUploadPolicy e) {
            e.printStackTrace();
        } catch (NotfoundUserModelException e) {
            e.printStackTrace();
        } catch (AuthenticationNotMatch authenticationNotMatch) {
            authenticationNotMatch.printStackTrace();
        }


    }
}
