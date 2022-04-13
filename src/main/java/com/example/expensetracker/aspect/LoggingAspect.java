package com.example.expensetracker.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {
    private static Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Pointcut("execution(* com.example.expensetracker.service.*.*(..))")
    public void serviceMethods() { }

    @Before("serviceMethods()")
    public void logServiceMethodsCalled(JoinPoint joinPoint) {
        String packageName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("Method [" + packageName + "." + methodName + "] called with args " + args + "...");
    }

    @AfterThrowing(value = "serviceMethods()", throwing = "exception")
    public void serviceMethodsThrow(Exception exception) {
        logger.severe("!!!LoggingAspect.serviceMethodsThrow() -> Error:" + exception + "!!!");
    }
}
