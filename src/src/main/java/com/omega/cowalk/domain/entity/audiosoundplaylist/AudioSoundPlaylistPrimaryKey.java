package com.omega.cowalk.domain.entity.audiosoundplaylist;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class AudioSoundPlaylistPrimaryKey implements Serializable {

    private String audioBookTitle;

    private long userId;

    private long soundId;
}
