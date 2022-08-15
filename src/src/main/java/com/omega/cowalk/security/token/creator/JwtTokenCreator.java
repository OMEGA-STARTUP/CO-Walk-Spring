package com.omega.cowalk.security.token.creator;

import com.omega.cowalk.security.auth.PrincipalUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenCreator implements TokenCreator {

    @Value("${cloud.aws.credentials.secret-key}")
    private String SECRET_KEY;

    @Override
    public String create(PrincipalUserDetails principalUserDetails, long expiredTime) {
        return Jwts.builder()
                .setSubject(principalUserDetails.getUser().getId().toString())
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .setClaims(createClaims(principalUserDetails))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    private Map<String, Object> createClaims(PrincipalUserDetails principalUserDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("identifier", principalUserDetails.getUser().getIdentifier());
        claims.put("role", principalUserDetails.getUser().getRole());

        return claims;
    }
}
