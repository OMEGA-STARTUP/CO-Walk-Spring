package com.omega.cowalk.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
@Getter
public class SendCodeForPasswordRequestDto {

    @NotNull(message = "identifier should not be null!")
    private final String identifier;

    @NotNull(message = "email should not be null!")
    @Email
    private final String email;

}
