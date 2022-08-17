package com.omega.cowalk.security.token.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtTokenIssueDto {

    private final String accessToken;
    private final String refreshToken;
}
