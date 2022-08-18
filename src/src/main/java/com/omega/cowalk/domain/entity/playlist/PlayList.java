package com.omega.cowalk.domain.entity.playlist;

import com.omega.cowalk.domain.entity.backgroundsound.BackgroundSound;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@ToString
@Table(name="playlist")
@Builder
@IdClass(PlayListPrimaryKey.class)
public class PlayList {
    @Id
    @Column(name="user_id")
    private final long user_id;

    @Id
    @Column(name = "sound_id")
    private final long sound_id;


    @Column(name = "order_numb")
    private final int order_number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sound_id", referencedColumnName= "sound_id", updatable = false, insertable = false)
    private final BackgroundSound backgroundSound;

}
