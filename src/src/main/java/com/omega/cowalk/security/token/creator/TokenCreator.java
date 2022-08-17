package com.omega.cowalk.security.token.creator;

import com.omega.cowalk.security.auth.PrincipalUserDetails;

public interface TokenCreator {
    String create(PrincipalUserDetails principalUserDetails, String key, long expiredTime);
}
