package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.notification.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NotificationRepository extends CrudRepository<Notification, Long>
{
    @Query("select n from Notification n where n.notificationId = :notificationId")
    Optional<Notification> findByNotificationId(long notificationId);
}
