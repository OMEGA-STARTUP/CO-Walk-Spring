package com.omega.cowalk.domain.dto;

import lombok.*;

import javax.validation.constraints.*;

// /user/register할때 RegisterRequestDto를 사용해야 되서 이름을 바꿧습니다.
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

    @NotNull(message = "jwt_token should not be null!")
    private String jwtToken;

}
