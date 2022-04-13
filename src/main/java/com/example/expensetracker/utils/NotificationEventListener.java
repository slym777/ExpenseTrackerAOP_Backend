package com.example.expensetracker.utils;

import com.example.expensetracker.events.NotificationCreationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class NotificationEventListener implements ApplicationListener<NotificationCreationEvent> {
    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void onApplicationEvent(NotificationCreationEvent event) {
        var notification = event.getNotification();
        logger.warning(String.format("Notification of type '%s' should be sent to user %s[email: %s]. Notification text: '%s'",
                notification.getType().toString(),
                notification.getUser().getFullName(),
                notification.getUser().getEmail(),
                notification.getDescription()
                )
        );
    }
}
