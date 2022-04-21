package com.linyonghao.linosscore.service.certificate;

import com.linyonghao.linosscore.model.UploadPolicy;
import com.linyonghao.linosscore.model.UserModel;

public interface UploadAuthenticator {
    String generateCertification(UserModel userModel, UploadPolicy uploadPolicy);
    UserModel decryptCertification(String key) throws NotKeyException, KeyNumberException;

}
