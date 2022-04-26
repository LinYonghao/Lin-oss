package com.linyonghao.linosscore.serivce;

import com.linyonghao.linosscore.dto.UploadCertificationDTO;
import com.linyonghao.linosscore.model.UploadPolicy;
import com.linyonghao.linosscore.model.UserModel;
import com.linyonghao.linosscore.service.certificate.*;
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
        userModel.setAccessKey("123");
        userModel.setSecretKey("321");

        UploadPolicy uploadPolicy = new UploadPolicy();
        uploadPolicy.setMINE("*");
        uploadPolicy.setKey("abc.png");
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

            System.out.println(uploadCertificationDTO.getUserModel());
            System.out.println(uploadCertificationDTO.getUploadPolicy());

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
