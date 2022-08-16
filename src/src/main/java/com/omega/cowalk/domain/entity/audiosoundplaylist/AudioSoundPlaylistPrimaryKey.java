package com.omega.cowalk.domain.entity.audiosoundplaylist;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class AudioSoundPlaylistPrimaryKey implements Serializable {

    private String audio_book_title;

    private long user_id;

    private long sound_id;
}
