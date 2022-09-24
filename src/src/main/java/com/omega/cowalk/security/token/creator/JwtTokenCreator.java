package com.omega.cowalk.security.token.creator;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.omega.cowalk.security.auth.PrincipalUserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenCreator implements TokenCreator {

    @Override
    public String create(PrincipalUserDetails principalUserDetails, String key, long expiredTime) {
        return JWT.create()
                .withSubject(principalUserDetails.getUser().getId().toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiredTime))
                .withClaim("identifier", principalUserDetails.getUser().getIdentifier())
                .withClaim("role", principalUserDetails.getUser().getRole().toString())
                .sign(Algorithm.HMAC512(key));
    }

    public String createTokenForSignUpSendRequest(String email, String access_code, String key, long expiredTime)
    {
        return JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiredTime))
                .withClaim("email", email)
                .withClaim("purpose_code", 0)
                .withClaim("access_code", access_code)
                .withClaim("isVerified", false)
                .sign(Algorithm.HMAC512(key));
    }

     public String createTokenForCheckSignUpCode(String email, String key, long expiredTime)
     {
         return JWT.create()
                 .withIssuedAt(new Date())
                 .withExpiresAt(new Date(System.currentTimeMillis() + expiredTime))
                 .withClaim("email", email)
                 .withClaim("purpose_code", 0)
                 .withClaim("isVerified", true)
                 .sign(Algorithm.HMAC512(key));
     }

    @Override
    public String createTokenForPasswordSendRequest(String identifier, String code, String key, long expiredTime) {
        return JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiredTime))
                .withClaim("identifier", identifier)
                .withClaim("purpose_code", 1)
                .withClaim("access_code", code)
                .withClaim("isVerified", false)
                .sign(Algorithm.HMAC512(key));
    }

    @Override
    public String createTokenForPasswordCheckRequest(String identifier, String key, long expiredTime) {
        return JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiredTime))
                .withClaim("identifier", identifier)
                .withClaim("purpose_code", 1)
                .withClaim("isVerified", true)
                .sign(Algorithm.HMAC512(key));
    }

}
