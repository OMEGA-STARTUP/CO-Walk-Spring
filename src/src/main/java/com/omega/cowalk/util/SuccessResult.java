package com.omega.cowalk.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SuccessResult {

    private final int code;
    private final String message;
    private Object object;
}
