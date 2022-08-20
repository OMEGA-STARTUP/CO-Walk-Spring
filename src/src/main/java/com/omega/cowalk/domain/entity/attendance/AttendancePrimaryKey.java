package com.omega.cowalk.domain.entity.attendance;

import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
public class AttendancePrimaryKey implements Serializable
{
    private java.sql.Date attendDate;

    private long userId;

    public AttendancePrimaryKey(java.sql.Date attend_date, long user_id)
    {
        this.attendDate = attend_date;
        this.userId = user_id;
    }
}
