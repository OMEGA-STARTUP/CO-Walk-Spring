package com.omega.cowalk.domain.entity.audiosoundplaylist;


import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@ToString
@Table(name="audio_sound_playlist")
@Builder
@IdClass(AudioSoundPlaylistPrimaryKey.class)
public class AudioSoundPlaylist
{
    @Id
    @Column(name = "audio_book_title")
    private final String audioBookTitle;

    @Id
    @Column(name="user_id")
    private final long userId;

    @Id
    @Column(name="sound_id")
    private long soundId;

    @Column(name="order_numb")
    private int orderNumb;
}
