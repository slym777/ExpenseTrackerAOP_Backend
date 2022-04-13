package com.example.expensetracker.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Aspect
@Component
public class PerformanceAspect {
    private static Logger logger = Logger.getLogger(PerformanceAspect.class.getName());

    @Pointcut("within(com.example.expensetracker.controller..*)")
    public void controllerMethods() {}

    @Before("controllerMethods()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        String packageName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        String message = "Method [" + packageName + "." + methodName + "] started at " + LocalDateTime.now();
        logger.info(message);
    }

    @After("controllerMethods()")
    public void afterControllerMethod(JoinPoint joinPoint) {
        String packageName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        String message = "Method [" + packageName + "." + methodName + "] ended at " + LocalDateTime.now();
        logger.info(message);
    }

    @Around("controllerMethods()")
    public Object measureMethodExecTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object returnedValue = proceedingJoinPoint.proceed();
        long endTime = System.nanoTime();

        String packageName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String methodName = proceedingJoinPoint.getSignature().getName();

        logger.info("Execution of method [" + packageName + "." + methodName + "] took " +
                TimeUnit.NANOSECONDS.toMillis(endTime - startTime) + "ms\n");
        return returnedValue;
    }
}
