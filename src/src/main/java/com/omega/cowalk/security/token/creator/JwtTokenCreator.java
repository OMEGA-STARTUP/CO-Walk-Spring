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
}
