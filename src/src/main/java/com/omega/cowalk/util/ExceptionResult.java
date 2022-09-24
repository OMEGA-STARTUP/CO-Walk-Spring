package com.omega.cowalk.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ExceptionResult
{
    private final Date timestamp;
    private final String msg;
    private final String details;
}
