package com.omega.cowalk.domain.entity.playlist;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class PlayListPrimaryKey implements Serializable {

    private long user_id;

    private long sound_id;
}
