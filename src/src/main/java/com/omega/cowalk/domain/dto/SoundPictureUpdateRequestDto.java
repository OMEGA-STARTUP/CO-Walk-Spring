package com.omega.cowalk.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class SoundPictureUpdateRequestDto
{
    @NotNull
    private final int sound_background_id;
}
