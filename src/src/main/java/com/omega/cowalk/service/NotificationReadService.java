package com.omega.cowalk.service;

import com.omega.cowalk.domain.entity.notificationread.NotificationRead;
import com.omega.cowalk.repository.NotificationReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationReadService {

    private final NotificationReadRepository notificationReadRepository;

    public void clickReadButton(long userId, long notificationId){
        notificationReadRepository.save(NotificationRead.builder()
                .notificationId(notificationId)
                .userId(userId)
                .build());
    }
}
