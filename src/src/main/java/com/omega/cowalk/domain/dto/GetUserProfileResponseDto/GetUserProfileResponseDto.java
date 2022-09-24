package com.omega.cowalk.domain.dto.GetUserProfileResponseDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
@Getter
@ToString
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetUserProfileResponseDto
{
    private final String nickname;

    private final String profileImgUrl;

    private final long profileBackgroundId;

    private final List<UserRecentHistory> userRecentHistories;

}
