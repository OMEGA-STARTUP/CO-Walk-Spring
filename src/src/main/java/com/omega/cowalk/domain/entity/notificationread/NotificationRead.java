package com.omega.cowalk.domain.entity.notificationread;

import com.omega.cowalk.domain.entity.notification.Notification;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="notification_read")
@Builder
@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@IdClass(NotificationReadPrimaryKey.class)
public class NotificationRead
{
    @Id
    @Column(name = "user_id")
    private final long userId;

    @Id
    @Column(name = "notification_id")
    private final long notificationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "notification_id", referencedColumnName = "notification_id", updatable = false, insertable = false)
    private final Notification notification;
}
