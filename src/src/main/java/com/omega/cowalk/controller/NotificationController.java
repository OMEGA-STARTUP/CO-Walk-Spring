package com.omega.cowalk.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.omega.cowalk.domain.dto.NotificationPageResponseDto;
import com.omega.cowalk.domain.dto.NotificationSendRequestDto;
import com.omega.cowalk.security.auth.PrincipalUserDetails;
import com.omega.cowalk.security.token.service.TokenService;
import com.omega.cowalk.service.FirebaseMessagingService;
import com.omega.cowalk.service.NotificationReadService;
import com.omega.cowalk.service.NotificationService;
import com.omega.cowalk.util.SuccessResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationReadService notificationReadService;
    private final FirebaseMessagingService messagingService;
    private final TokenService tokenService;

    // 관리자가 푸쉬알림을 전송하는 API
    @PostMapping
    public ResponseEntity<Object> sendNotification(
            @RequestBody NotificationSendRequestDto sendRequestDto,
            @RequestHeader("APP_TOKEN") String token) throws FirebaseMessagingException {
        String message = messagingService.sendNotification(sendRequestDto, token);
        log.info("send push notification={}", message);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<SuccessResult> getNotifications(
            @RequestHeader("ACCESS_TOKEN") String accessToken){
        PrincipalUserDetails principalUserDetails = tokenService.verifyToken(accessToken, tokenService.getSECRET_KEY());
        List<NotificationPageResponseDto> notificationPageResponseDtos =
                notificationService.searchNotifications(principalUserDetails.getUser().getId());
        return ResponseEntity.ok(new SuccessResult(notificationPageResponseDtos));
    }

    @GetMapping("/{notification-id}")
    public ResponseEntity<SuccessResult> getNotification(
            @RequestHeader("ACCESS_TOKEN") String accessToken,
            @PathVariable("notification-id") long notificationId){
        PrincipalUserDetails principalUserDetails = tokenService.verifyToken(accessToken, tokenService.getSECRET_KEY());
        NotificationPageResponseDto notificationPageResponseDto =
                notificationService.searchNotification(principalUserDetails.getUser().getId(), notificationId);
        return ResponseEntity.ok(new SuccessResult(notificationPageResponseDto));
    }

    @PutMapping("/{notification-id}")
    public ResponseEntity<Object> readNotification(
            @RequestHeader("ACCESS_TOKEN") String accessToken,
            @PathVariable("notification-id") long notificationId){
        PrincipalUserDetails principalUserDetails = tokenService.verifyToken(accessToken, tokenService.getSECRET_KEY());
        notificationReadService.clickReadButton(principalUserDetails.getUser().getId(), notificationId);

        return ResponseEntity.ok().build();
    }
}
