package com.omega.cowalk.repository;

import com.omega.cowalk.domain.dto.NotificationPageResponseDto;
import com.omega.cowalk.domain.entity.notification.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends CrudRepository<Notification, Long>
{
    @Query("select n from Notification n where n.notificationId = :notificationId")
    Optional<Notification> findByNotificationId(long notificationId);

    @Query("select new com.omega.cowalk.domain.dto.NotificationPageResponseDto(" +
            "n.notificationId, n.title, n.content, CASE WHEN (nr.userId = :userId) THEN true ELSE false END)" +
            " from Notification n" +
            " left join NotificationRead nr on n.notificationId = nr.notificationId" +
            " where nr.userId = :userId or nr.userId is null")
    List<NotificationPageResponseDto> findNotificationInfosByUserId(long userId);

    @Query("select new com.omega.cowalk.domain.dto.NotificationPageResponseDto(" +
            " n.notificationId, n.title, n.content, CASE WHEN (nr.userId = :userId) THEN true ELSE false END)" +
            " from Notification n" +
            " left join NotificationRead nr on n.notificationId = nr.notificationId" +
            " where n.notificationId = :notificationId and nr.userId = :userId")
    Optional<NotificationPageResponseDto> findNotificationInfoByUserId(long userId, long notificationId);
}
