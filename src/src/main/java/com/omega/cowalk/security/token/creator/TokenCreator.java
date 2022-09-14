package com.omega.cowalk.security.token.creator;

import com.omega.cowalk.security.auth.PrincipalUserDetails;

public interface TokenCreator {
    String create(PrincipalUserDetails principalUserDetails, String key, long expiredTime);

    public String createTokenForSignUpSendRequest(String email, String code, String key, long expiredTime);

    public String createTokenForCheckSignUpCode(String email, String key, long expiredTime);

    public String createTokenForPasswordSendRequest(String identifier, String code, String key, long expiredTime);

    public String createTokenForPasswordCheckRequest(String identifier, String key, long expiredTime);



}
