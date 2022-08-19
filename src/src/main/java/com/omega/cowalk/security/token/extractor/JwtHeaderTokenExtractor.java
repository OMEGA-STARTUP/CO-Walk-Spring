package com.omega.cowalk.security.token.extractor;

import com.omega.cowalk.security.exceptions.JwtNotFoundException;
import com.omega.cowalk.security.token.JwtTokenProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtHeaderTokenExtractor implements TokenExtractor{

    @Override
    public String extract(String jwtTokenHeader) throws JwtNotFoundException {
        if(!StringUtils.hasText(jwtTokenHeader)){
            throw new JwtNotFoundException("Authorization header cannot be blank!");
        }

        if(jwtTokenHeader.length() < JwtTokenProperties.HEADER_PREFIX.length()){
            throw new JwtNotFoundException("Invalid authorization header size");
        }

        if(!jwtTokenHeader.startsWith(JwtTokenProperties.HEADER_PREFIX)){
            throw new JwtNotFoundException("Invalid token format");
        }

        return jwtTokenHeader.substring(JwtTokenProperties.HEADER_PREFIX.length());
    }

}
