package com.dawid.notification.Service;

import com.poleszak.clients.notification.NotificationRequest;
import com.dawid.notification.Entity.Notification;
import com.dawid.notification.Repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final Clock clock;

    public void createNotification(NotificationRequest notificationRequest) {
        Notification notification = Notification.builder()
                .toCustomerId(notificationRequest.toCustomerId())
                .toCustomerEmail(notificationRequest.toCustomerEmail())
                .sender("Pioter")
                .message(notificationRequest.message())
                .sentAt(now(clock))
                .build();
        notificationRepository.save(notification);
    }
}
