package com.omega.cowalk.security.token;

public interface JwtTokenProperties {
    String HEADER_PREFIX = "Bearer ";
    String HEADER_ACCESS_KEY = "ACCESS_TOKEN";
    String HEADER_REFRESH_KEY = "REFRESH_TOKEN";
    Long ACCESS_TOKEN_EXPIRED_TIME = 60000L * 5;
    Long REFRESH_TOKEN_EXPIRED_TIME = 60000L * 60 * 24 * 31;
}
