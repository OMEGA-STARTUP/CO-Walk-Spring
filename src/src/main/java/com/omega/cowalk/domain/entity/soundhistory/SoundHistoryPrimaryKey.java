package com.omega.cowalk.domain.entity.soundhistory;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class SoundHistoryPrimaryKey implements Serializable {

    private long user_id;

    private long sound_id;

    private java.sql.Date listen_date;
}
