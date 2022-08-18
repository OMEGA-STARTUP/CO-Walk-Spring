package com.omega.cowalk.security.token.extractor;

import com.omega.cowalk.security.exceptions.JwtNotFoundException;

public interface TokenExtractor {
    String extract(String payload) throws JwtNotFoundException;
}
