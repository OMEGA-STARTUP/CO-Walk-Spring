package com.omega.cowalk.security.token.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtTokenReIssueDto {

    private final String accessToken;
}
