package com.example.expensetracker.events;

import com.example.expensetracker.model.Notification;
import org.springframework.context.ApplicationEvent;

public class NotificationCreationEvent extends ApplicationEvent {
    Notification notification;

    public NotificationCreationEvent(Object source, Notification notification) {
        super(source);
        this.notification = notification;
    }

    public Notification getNotification() {
        return notification;
    }
}
