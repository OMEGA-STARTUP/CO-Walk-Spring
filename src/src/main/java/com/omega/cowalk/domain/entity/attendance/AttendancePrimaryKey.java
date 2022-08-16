package com.omega.cowalk.domain.entity.attendance;

import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
public class AttendancePrimaryKey implements Serializable
{
    private java.sql.Date attend_date;

    private long user_id;

    public AttendancePrimaryKey(java.sql.Date attend_date, long user_id)
    {
        this.attend_date = attend_date;
        this.user_id = user_id;
    }
}
