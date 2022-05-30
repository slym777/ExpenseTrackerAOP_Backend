package com.example.expensetracker.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class ExceptionHandlingAspect {
    private static Logger logger = Logger.getLogger(ExceptionHandlingAspect.class.getName());

    @Pointcut("execution(* com.example.expensetracker.service.*.*(..))")
    public void serviceMethods() { }

    @AfterThrowing(value = "serviceMethods()", throwing = "exception")
    public void serviceMethodsThrow(Exception exception) {
        logger.severe("!!!Error:" + exception + "!!!");
    }
}
