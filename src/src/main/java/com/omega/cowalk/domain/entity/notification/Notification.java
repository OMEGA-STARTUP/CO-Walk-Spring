package com.omega.cowalk.domain.entity.notification;


import com.omega.cowalk.domain.entity.notificationread.NotificationRead;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@ToString
@Table(name="notification")
@Builder
public class Notification
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notification_id")
    private final long notificationId;

    @Column(name="title")
    private final String title;

    @Column(name = "content")
    private final String content;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
    public List<NotificationRead> notificationReads = new ArrayList<>();
}
