package com.omega.cowalk.domain.entity.walkhistory;

import lombok.AllArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
public class WalkHistoryPrimaryKey {

    private Date walkDate;
    private long userId;
}
