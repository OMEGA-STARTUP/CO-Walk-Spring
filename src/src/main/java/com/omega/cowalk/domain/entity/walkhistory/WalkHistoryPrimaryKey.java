package com.omega.cowalk.domain.entity.walkhistory;

import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class WalkHistoryPrimaryKey {

    private LocalDate walkDate;
    private long userId;
}
