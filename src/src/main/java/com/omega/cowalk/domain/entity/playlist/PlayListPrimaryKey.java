package com.omega.cowalk.domain.entity.playlist;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class PlayListPrimaryKey implements Serializable {

    private long userId;

    private long soundId;
}
