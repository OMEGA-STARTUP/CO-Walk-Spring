package com.omega.cowalk.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
@Getter
@ToString
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CheckCodeForPasswordRequestDto {

    @NotNull(message = "user_access_code not found!")
    private final String userAccessCode;

    @NotNull(message = "jwt_token not found!")
    private final String jwtToken;
}
