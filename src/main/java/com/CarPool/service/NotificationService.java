package com.CarPool.service;

public interface NotificationService {
    void sendNotification(String toEmail, String subject, String body);
}
