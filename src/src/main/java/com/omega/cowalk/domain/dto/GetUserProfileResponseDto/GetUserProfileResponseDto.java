package com.omega.cowalk.domain.dto.GetUserProfileResponseDto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
@Getter
@ToString
public class GetUserProfileResponseDto
{
    private final String nickname;

    private final String profile_img_url;

    private final int sound_count;

    private final long profile_background_id;

    private final List<UserRecentHistory> userRecentHistories;

}
