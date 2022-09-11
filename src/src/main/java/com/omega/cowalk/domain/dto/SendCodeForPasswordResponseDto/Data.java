package com.omega.cowalk.domain.dto.SendCodeForPasswordResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
@Getter
public class Data {

    private final String jwt_token;
}
