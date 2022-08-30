package com.omega.cowalk.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.omega.cowalk.domain.dto.NotificationSendRequestDto;
import com.omega.cowalk.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;
    private final NotificationRepository notificationRepository;

    public String sendNotification(NotificationSendRequestDto sendRequestDto, String token) throws FirebaseMessagingException {
        // 알림 정보 세팅
        Notification notification = Notification
                .builder()
                .setTitle(sendRequestDto.getTitle())
                .setBody(sendRequestDto.getContent())
                .build();
        //메시지 정보 세팅
        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        notificationRepository.save(com.omega.cowalk.domain.entity.notification.Notification
                .builder()
                .title(sendRequestDto.getTitle())
                .content(sendRequestDto.getContent())
                .build());

        return firebaseMessaging.send(message);
    }
}
