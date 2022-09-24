package com.omega.cowalk.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterCheckCodeRequestDto {

    @NotNull(message = "user_access_code should not be null or blank")
    @Size(min=6, max =6, message = "the length of access_code should be 6")
    private final String userAccessCode;

    @NotNull(message = "jwt_token should not be null or blank")
    private final String jwtToken;
}
