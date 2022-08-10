package com.omega.cowalk.domain.dto;

import com.omega.cowalk.domain.entity.Role;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CowalkUserDto {

    private long user_id;

    @NotNull(message = "identifier should not be null")
    @Size(min = 8, max = 20, message = "length of password not appropriate")
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

    private Role role;

    @Pattern(regexp = "http.*", message = "url should start with the word http")
    private String sound_background_img_url;

    @Pattern(regexp = "http.*", message = "url should start with the word http")
    private String profile_img_url;

}
