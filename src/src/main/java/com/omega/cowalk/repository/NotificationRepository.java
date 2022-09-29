package com.omega.cowalk.repository;

import com.omega.cowalk.domain.dto.NotificationPageResponseDto;
import com.omega.cowalk.domain.entity.notification.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends CrudRepository<Notification, Long>
{

    @Query("select new com.omega.cowalk.domain.dto.NotificationPageResponseDto(" +
            " n.notificationId, n.title, n.content," +
            " (exists (select nr from NotificationRead nr where nr.userId = :userId and n.notificationId = nr.notificationId))" +
            ")" +
            " from Notification n")
    List<NotificationPageResponseDto> findNotificationInfosByUserId(long userId);

    @Query("select new com.omega.cowalk.domain.dto.NotificationPageResponseDto(" +
            " n.notificationId, n.title, n.content," +
            " (exists (select nr from NotificationRead nr where nr.userId = :userId and n.notificationId = :notificationId))" +
            ")" +
            " from Notification n" +
            " where n.notificationId = :notificationId")
    Optional<NotificationPageResponseDto> findNotificationInfoByUserId(long userId, long notificationId);
}
