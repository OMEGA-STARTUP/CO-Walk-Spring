package com.omega.cowalk.domain.entity.backgroundsound;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
public class SteppingSound implements Serializable
{
    private final String stepping_sound_img_url;

    private final String stepping_sound_name;

    private final String stepping_sound_play_url;
}
