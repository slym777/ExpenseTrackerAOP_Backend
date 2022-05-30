package com.example.expensetracker.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {
    private static Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Pointcut("execution(* com.example.expensetracker.service.*.*(..))")
    public void serviceMethods() {
    }

    @Pointcut(
            "within(@org.springframework.web.bind.annotation.RestController *) && " +
                    "@annotation(getMapping) && " +
                    "execution(* *(..))"
    )
    public void controllerGETEndpoints(GetMapping getMapping) { }

    @Before("serviceMethods()")
    public void logServiceMethodsCalled(JoinPoint joinPoint) {
        String packageName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("Method [" + packageName + "." + methodName + "] called with args " + args + "...");
    }

    @Before("controllerGETEndpoints(getMapping)")
    public void logControllerEndpointTriggeredResult(JoinPoint joinPoint, GetMapping getMapping) {
        logger.info("An endpoint was triggered with GET request method! Endpoint: \"" + Arrays.stream(getMapping.value()).findFirst().get() + "\"");
    }
}


