package com.omega.cowalk.domain.dto;

import lombok.*;

import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    @NotNull(message = "identifier should not be null")
    @Size(min = 8, max = 20, message = "length of identifier not appropriate")
    @Pattern(regexp = "[A-Za-z0-9]+", message = "password should be in english and number")
    private String identifier;

    @NotNull(message = "password should not be null")
    @Size(min = 8, max = 20, message = "length of password not appropriate")
    @Pattern(regexp = "[A-Za-z0-9]+", message = "password should be in english and number")
    private String password;

    @Email(message = "email format is not correct!")
    @NotNull(message = "email should not be null!")
    private String email;

    @NotNull(message = "nickname should not be null!")
    private String nickname;


    @Pattern(regexp = "http.*", message = "url should start with the word http")
    private String sound_background_img_url;

    @Pattern(regexp = "http.*", message = "url should start with the word http")
    private String profile_img_url;

}
