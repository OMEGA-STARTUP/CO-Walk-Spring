package com.omega.cowalk.domain.entity.backgroundsound;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
public class SteppingSounds
{
    public final List<SteppingSound> steppingSounds;
}
