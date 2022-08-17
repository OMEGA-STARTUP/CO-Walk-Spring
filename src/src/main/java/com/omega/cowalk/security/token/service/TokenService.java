package com.omega.cowalk.security.token.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.omega.cowalk.security.auth.PrincipalUserDetails;
import com.omega.cowalk.security.exceptions.JwtMalformedException;
import com.omega.cowalk.security.exceptions.JwtNotFoundException;
import com.omega.cowalk.security.token.JwtTokenProperties;
import com.omega.cowalk.security.token.creator.TokenCreator;
import com.omega.cowalk.security.token.extractor.TokenExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${cloud.aws.credentials.secret-key}")
    private String SECRET_KEY;
    @Value("${cloud.aws.credentials.access-key}")
    private String REFRESH_KEY;

    private final TokenCreator jwtTokenCreator;
    private final TokenExtractor jwtHeaderTokenExtractor;

    public String verifyToken(String jwtTokenHeader, String keyType) throws JWTVerificationException {
        // Header 검증
        String jwtToken = verifyHeader(jwtTokenHeader);
        // Jwt Refresh Token 검증
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(keyType)).build().verify(jwtToken);
        String identifier = decodedJWT.getClaim("identifier").asString();
        if(identifier == null){
            throw new JwtMalformedException("Jwt token malformed");
        }

        return identifier;
    }

    private String verifyHeader(String jwtTokenHeader) throws JwtNotFoundException {
        return jwtHeaderTokenExtractor.extract(jwtTokenHeader);
    }

    public Map<String, String> issue(PrincipalUserDetails principalUserDetails){
        log.debug("토큰 발급 요청");
        String jwtAccessToken = jwtTokenCreator
                .create(principalUserDetails, SECRET_KEY, JwtTokenProperties.ACCESS_TOKEN_EXPIRED_TIME);
        String jwtRefreshToken = jwtTokenCreator
                .create(principalUserDetails, REFRESH_KEY, JwtTokenProperties.REFRESH_TOKEN_EXPIRED_TIME);

        Map<String, String> jwtTokenStore = new HashMap<>();
        jwtTokenStore.put(JwtTokenProperties.HEADER_ACCESS_KEY, JwtTokenProperties.HEADER_PREFIX + jwtAccessToken);
        jwtTokenStore.put(JwtTokenProperties.HEADER_REFRESH_KEY, JwtTokenProperties.HEADER_PREFIX + jwtRefreshToken);

        return jwtTokenStore;
    }

    public String reIssue(PrincipalUserDetails principalUserDetails){
        log.debug("토큰 재발급 요청");
        String jwtAccessToken = jwtTokenCreator
                .create(principalUserDetails, SECRET_KEY, JwtTokenProperties.ACCESS_TOKEN_EXPIRED_TIME);

        return JwtTokenProperties.HEADER_PREFIX + jwtAccessToken;
    }
}
