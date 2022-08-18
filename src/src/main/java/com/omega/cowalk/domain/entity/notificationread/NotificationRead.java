package com.omega.cowalk.domain.entity.notificationread;

import com.omega.cowalk.domain.entity.notification.Notification;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@ToString
@Table(name="notification_read")
@Builder
@IdClass(NotificationReadPrimaryKey.class)
public class NotificationRead
{
    @Id
    @Column(name = "user_id")
    private final long user_id;

    @Id
    @Column(name = "notification_id")
    private final long notification_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "notification_id", referencedColumnName = "notification_id", updatable = false, insertable = false)
    private final Notification notification;
}
