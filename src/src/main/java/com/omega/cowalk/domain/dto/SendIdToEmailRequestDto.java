package com.omega.cowalk.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class SendIdToEmailRequestDto
{
    @NotNull
    @Email(message = "not correct email format")
    private final String email;
}
