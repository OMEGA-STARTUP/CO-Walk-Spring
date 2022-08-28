package com.omega.cowalk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class NotificationSendRequestDto {

    private String title;
    private String content;
}
