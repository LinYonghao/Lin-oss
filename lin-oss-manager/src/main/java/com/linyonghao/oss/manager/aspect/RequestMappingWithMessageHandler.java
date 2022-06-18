package com.linyonghao.oss.manager.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect
public class RequestMappingWithMessageHandler {
    @Pointcut("@annotation(com.linyonghao.oss.manager.annotation.RequestMappingWithMessage)")
    public void pointcut(){}

    @Around(value = "pointcut()")
    public Object Around(ProceedingJoinPoint joinPoint) throws Throwable {

        return joinPoint.proceed();
    }

}
