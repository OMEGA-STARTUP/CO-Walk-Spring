package com.omega.cowalk.domain.entity.notification;


import lombok.*;

import javax.persistence.*;

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
    private final long notification_id;

    @Column(name="title")
    private final String title;

    @Column(name = "content")
    private final String content;
}
