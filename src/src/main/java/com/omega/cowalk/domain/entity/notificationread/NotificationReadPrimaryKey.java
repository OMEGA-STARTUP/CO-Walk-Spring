package com.omega.cowalk.domain.entity.notificationread;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class NotificationReadPrimaryKey implements Serializable
{

    private long userId;

    private long notificationId;
}
