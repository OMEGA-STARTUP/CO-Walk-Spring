package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.notification.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NotificationRepository extends CrudRepository<Notification, Long>
{
    @Query("select n from Notification n where n.notification_id = ?1")
    public Optional<Notification> findByNotification_id(long notification_id);
}