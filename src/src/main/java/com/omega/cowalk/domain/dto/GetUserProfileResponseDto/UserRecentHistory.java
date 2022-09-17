package com.omega.cowalk.domain.dto.GetUserProfileResponseDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.omega.cowalk.domain.entity.soundhistory.SoundHistory;
import lombok.*;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
@Getter
@ToString
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRecentHistory
{
    private final long soundId;

    private final String soundName;

    private final String soundImgUrl;

    private final String latestListenTime;

    private final int actualPlayTime;

    public UserRecentHistory(SoundHistory soundHistory)
    {
        this.soundId = soundHistory.getBackgroundSound().getSoundId();
        this.soundName = soundHistory.getBackgroundSound().getSoundName();
        this.soundImgUrl = soundHistory.getBackgroundSound().getSoundImgUrl();
        this.latestListenTime = soundHistory.getLatestListenTime().toString();
        this.actualPlayTime = soundHistory.getActualPlayTime();

    }
}
