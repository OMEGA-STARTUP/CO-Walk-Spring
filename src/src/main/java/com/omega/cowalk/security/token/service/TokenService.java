package com.omega.cowalk.security.token.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.omega.cowalk.security.auth.PrincipalUserDetails;
import com.omega.cowalk.security.exceptions.InvalidAccessCodeException;
import com.omega.cowalk.security.exceptions.JwtMalformedException;
import com.omega.cowalk.security.exceptions.JwtNotFoundException;
import com.omega.cowalk.security.token.JwtTokenProperties;
import com.omega.cowalk.security.token.creator.TokenCreator;
import com.omega.cowalk.security.token.extractor.TokenExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
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


    private final PasswordEncoder passwordEncoder;
    private final TokenCreator jwtTokenCreator;
    private final TokenExtractor jwtHeaderTokenExtractor;

    public String getSECRET_KEY() {
        return SECRET_KEY;
    }

    public String getREFRESH_KEY() {
        return REFRESH_KEY;
    }

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

    public String issueSignUpEmailSendCodeToken(String email, String code)
    {
        log.debug("이메일 요청 보내기 토큰 발급");
        String jwtSendCodeToken = jwtTokenCreator.createTokenForSignUpSendRequest(
                email, passwordEncoder.encode(code), SECRET_KEY, JwtTokenProperties.SIGNUP_SEND_REQUEST_TOKEN_EXPIRED_TIIME);

        return JwtTokenProperties.HEADER_PREFIX + jwtSendCodeToken;
    }

    public String issueSignUpEmailVerifyCodeToken(String email)
    {
        log.debug("이메일 코드 승인 토큰 발급");
        String jwtSendCodeToken = jwtTokenCreator.createTokenForCheckSignUpCode(email, SECRET_KEY, JwtTokenProperties.SIGNUP_CHECK_CODE_TOKEN_EXPIRED_TIME);

        return JwtTokenProperties.HEADER_PREFIX + jwtSendCodeToken;
    }

    public String verifySignUpEmailSendCodeToken(String token, String code)
    {
        log.debug("이메일 코드 확인하고 이메일 리턴시작");

        token = verifyHeader(token);
        DecodedJWT decodedJWT = getDecodedJWT(token, SECRET_KEY);
        String encodedCode = decodedJWT.getClaim("access_code").asString();
        int purposeCode = decodedJWT.getClaim("purpose_code").asInt();

        if(encodedCode == null)
        {
            throw new JwtMalformedException("token missing some fields");
        }
        if(purposeCode != 0 )
        {
            throw new JWTVerificationException("purpose_code is not right");
        }

        if(!passwordEncoder.matches(code, encodedCode))
        {
            throw new InvalidAccessCodeException("email code does not match!");
        }

        String email = decodedJWT.getClaim("email").asString();
        if(email == null)
        {
            throw new JwtMalformedException("token missing email field");
        }

        return email;

    }

    public String verifySignUpEmailCheckCodeToken(String token, String unVerifiedEmail)
    {
        log.debug("이메일 코드확인 후 발급된 토큰을 verify 시작");

        token = verifyHeader(token);
        DecodedJWT decodedJWT = getDecodedJWT(token, SECRET_KEY);
        boolean isVerified = decodedJWT.getClaim("isVerified").asBoolean();
        int purposeCode = decodedJWT.getClaim("purpose_code").asInt();

        if(!isVerified)
        {
            throw new JWTVerificationException("the access_code is not verified");
        }
        if(purposeCode != 0 )
        {
            throw new JWTVerificationException("purpose_code is not right");
        }

        String email = decodedJWT.getClaim("email").asString();
        if(email == null)
        {
            throw new JwtMalformedException("token missing email field");
        }

        if(!email.equals(unVerifiedEmail))
        {
            throw new JWTVerificationException("email does not match with the email in jwt token!");
        }

        return email;

    }

    private DecodedJWT getDecodedJWT(String jwtToken, String key)
    {
        return JWT.require(Algorithm.HMAC512(key)).build().verify(jwtToken);
    }
}
