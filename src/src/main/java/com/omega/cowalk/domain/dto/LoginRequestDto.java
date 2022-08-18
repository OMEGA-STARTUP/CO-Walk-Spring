package com.omega.cowalk.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "identifier should not be blank")
    @Size(min = 8, max = 20, message = "length of identifier not appropriate")
    private final String identifier;

    @NotBlank(message = "password should not be blank")
    @Size(min = 8, max = 20, message = "length of identifier not appropriate")
    private final String password;
}
