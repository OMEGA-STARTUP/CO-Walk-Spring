package com.omega.cowalk.domain.entity.soundhistory;

import com.omega.cowalk.domain.entity.backgroundsound.BackgroundSound;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="sound_history")
@Builder
@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@IdClass(SoundHistoryPrimaryKey.class)
public class SoundHistory {

    @Id
    @Column(name = "user_id")
    private final long userId;

    @Id// FK를 PK로 사용할때 쓰는 애노테이션
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sound_id", referencedColumnName = "sound_id", updatable = false, insertable = false)
    private final BackgroundSound backgroundSound;

    @Id
    @Column(name = "listen_date")
    private final java.sql.Date listenDate;

    @Column(name="latest_listen_time", nullable = false)
    private final java.sql.Time latestListenTime;

    @Column(name = "actual_play_time", nullable = false)
    private final int actualPlayTime;



}
