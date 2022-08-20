package com.omega.cowalk.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class RegisterCheckCodeResponseDto {

    private final String jwtToken;
}
