package com.linyonghao.linosscore.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;


/**
 * 凭证生成器
 */
public class CertGenerator {
    /**
     * 随机Key生成器 用于生成唯一id
     * Base664(UUID())
     */
    public static String randomKey(){
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        byte[] encode = Base64.getEncoder().encode(s.getBytes(StandardCharsets.UTF_8));
        String key = new String(encode,StandardCharsets.UTF_8);
        return key;
    }

}
