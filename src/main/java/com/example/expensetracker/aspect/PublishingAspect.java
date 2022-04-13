package com.example.expensetracker.aspect;

import com.example.expensetracker.events.NotificationCreationEvent;
import com.example.expensetracker.model.Notification;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class PublishingAspect {
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Pointcut("@within(org.springframework.stereotype.Repository)")
    public void repositoryMethods() {}

    @Pointcut("execution(* com.example.expensetracker.repository.NotificationRepository.save(..))")
    public void saveNotificationsMethods() {}

    @Pointcut("repositoryMethods() && saveNotificationsMethods()")
    public void notificationEntitiesCreationMethods() {}

    @AfterReturning(value = "notificationEntitiesCreationMethods()", returning = "notification")
    public void publishNotificationEvent(JoinPoint joinPoint, Notification notification) throws Throwable {
        eventPublisher.publishEvent(new NotificationCreationEvent(this, notification));
    }
}
