package com.linyonghao.oss.core.utils;

import com.linyonghao.oss.core.util.CertGenerator;
import org.junit.jupiter.api.Test;

public class CertGeneratorTest {

    @Test
    public void generateAccessKey(){
        System.out.println("CertGenerator.generateAccessKey() = " + CertGenerator.randomKey());
    }


}
