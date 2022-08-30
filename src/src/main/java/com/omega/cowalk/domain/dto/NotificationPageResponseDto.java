package com.omega.cowalk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class NotificationPageResponseDto {

    private long notificationId;
    private String title;
    private String content;
    private Boolean isRead;
}
