package com.example.oopfiles;

import java.util.List;

// Observer interface
public interface NotificationObserver {
    List<Notification> receiveNotification(int message);
}

