package com.omega.cowalk.domain.dto.CheckCodeForPasswordResponseDto;

import com.omega.cowalk.domain.dto.SendCodeForPasswordResponseDto.Data;
import lombok.*;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CheckCodeForPasswordResponseDto {
    private final Data data;
}
