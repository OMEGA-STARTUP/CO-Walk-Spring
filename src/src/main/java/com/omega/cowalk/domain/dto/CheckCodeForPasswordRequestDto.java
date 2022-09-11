package com.omega.cowalk.domain.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CheckCodeForPasswordRequestDto {

    @NotNull(message = "user_access_code not found!")
    private final String user_access_code;

    @NotNull(message = "jwt_token not found!")
    private final String jwt_token;
}
