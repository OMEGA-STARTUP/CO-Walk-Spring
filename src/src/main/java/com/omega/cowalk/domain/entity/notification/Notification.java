package com.omega.cowalk.domain.entity.notification;


import com.omega.cowalk.domain.entity.notificationread.NotificationRead;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Table(name = "notification")
@Builder
@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Notification
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private final long notificationId;

    @Column(name = "title", nullable = false)
    private final String title;

    @Column(name = "content", nullable = false)
    private final String content;

    @OneToMany(mappedBy = "notification", cascade = ALL, fetch = EAGER)
    public List<NotificationRead> notificationReads = new ArrayList<>();
}
