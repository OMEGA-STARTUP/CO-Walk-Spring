package com.omega.cowalk.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WalkHistoryResponseDto {

    private LocalDate walkDate;
    private long steps;
}
