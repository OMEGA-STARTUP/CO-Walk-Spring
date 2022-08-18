package com.omega.cowalk.domain.entity.notificationread;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class NotificationReadPrimaryKey implements Serializable
{

    private long user_id;

    private long notification_id;
}
