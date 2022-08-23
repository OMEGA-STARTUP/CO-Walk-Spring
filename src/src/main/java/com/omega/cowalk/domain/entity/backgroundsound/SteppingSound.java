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
    private final String steppingSoundImgUrl;

    private final String steppingSoundName;

    private final String steppingSoundPlayUrl;
}
