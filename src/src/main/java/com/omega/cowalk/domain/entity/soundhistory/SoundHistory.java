package com.omega.cowalk.domain.entity.soundhistory;

import com.omega.cowalk.domain.entity.backgroundsound.BackgroundSound;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@ToString
@Table(name="sound_history")
@Builder
@IdClass(SoundHistoryPrimaryKey.class)
public class SoundHistory {
    @Id
    @Column(name = "user_id")
    private final long user_id;

    @Id
    @Column(name="sound_id")
    private final long sound_id;

    @Id
    @Column(name = "listen_date")
    private final java.sql.Date listen_date;

    @Column(name="latest_listen_time")
    private final java.sql.Time latest_listen_time;

    @Column(name = "actual_play_time")
    private final int actual_play_time;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sound_id", referencedColumnName= "sound_id", updatable = false, insertable = false)
    private final BackgroundSound backgroundSound;


}
