package com.omega.cowalk.domain.entity.soundhistory;

import com.omega.cowalk.domain.entity.backgroundsound.BackgroundSound;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class SoundHistoryPrimaryKey implements Serializable {

    private long userId;

    private BackgroundSound backgroundSound;

    private java.sql.Date listenDate;
}
