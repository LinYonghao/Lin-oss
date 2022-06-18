package com.linyonghao.oss.manager.annotation;


import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping()
public @interface RequestMappingWithMessage {

    @AliasFor(annotation = RequestMapping.class,attribute = "value")
    public String value() default "";
}
