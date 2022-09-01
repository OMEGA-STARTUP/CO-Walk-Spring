package com.omega.cowalk.domain.dto.GetUserProfileResponseDto;

import com.omega.cowalk.domain.entity.soundhistory.SoundHistory;
import lombok.*;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserRecentHistory
{
    private final long sound_id;

    private final String sound_name;

    private final String sound_img_url;

    private final String latest_listen_time;

    private final int actual_play_time;

    public UserRecentHistory(SoundHistory soundHistory)
    {
        this.sound_id = soundHistory.getSound_id();
        this.sound_name = soundHistory.getBackgroundSound().getSoundName();
        this.sound_img_url = soundHistory.getBackgroundSound().getSoundImgUrl();
        this.latest_listen_time = soundHistory.getLatest_listen_time().toString();
        this.actual_play_time = soundHistory.getActual_play_time();

    }
}
