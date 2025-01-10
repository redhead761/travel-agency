package com.epam.finaltask.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingDebugAspect {
    @Pointcut("execution(public * com.epam.finaltask.service..*(..))")
    public void servicePackage() {
    }

    @Pointcut("execution(public * com.epam.finaltask.controller..*(..))")
    public void controllerPackage() {
    }

    @Before("servicePackage() || controllerPackage()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        log.info("Method {}.{} was called", signature.getDeclaringTypeName(), signature.getName());
    }

    @AfterReturning("servicePackage() || controllerPackage()")
    public void after(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        log.info("Method {}.{} successfully executed", signature.getDeclaringTypeName(), signature.getName());
    }
}
