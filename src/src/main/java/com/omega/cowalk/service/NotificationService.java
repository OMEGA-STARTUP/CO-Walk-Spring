package com.omega.cowalk.service;

import com.omega.cowalk.domain.dto.NotificationPageResponseDto;
import com.omega.cowalk.exception.NotificationNotFoundException;
import com.omega.cowalk.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<NotificationPageResponseDto> searchNotifications(long userId){
        return notificationRepository.findNotificationInfosByUserId(userId);
    }

    public NotificationPageResponseDto searchNotification(long userId, long notificationId){
        return notificationRepository.findNotificationInfoByUserId(userId, notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("알림을 찾을 수 없습니다."));
    }
}
