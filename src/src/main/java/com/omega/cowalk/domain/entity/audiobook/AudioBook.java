package com.omega.cowalk.domain.entity.audiobook;


import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@ToString
@Table(name="audio_book")
@Builder
@IdClass(AudioBookPrimaryKey.class)
public class AudioBook {

    @Id
    @Column(name = "audio_book_title")
    private final String audioBookTitle;

    @Id
    @Column(name = "user_id")
    private final long userId;

}
