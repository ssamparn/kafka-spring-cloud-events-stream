package com.explore.springcloudeventsstreamplayground.sec08.dto;

public record Notification(int orderId,
                           NotificationChannel channel,
                           String recipient) {
}