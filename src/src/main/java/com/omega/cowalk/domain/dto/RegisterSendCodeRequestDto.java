package com.omega.cowalk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class RegisterSendCodeRequestDto {

    @Email(message = "it is not a valid email foramt")
    @NotNull
    private final String email;
}
