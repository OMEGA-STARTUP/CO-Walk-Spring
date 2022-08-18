package com.omega.cowalk.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResult {

    private final int code;
    private final String message;
}
