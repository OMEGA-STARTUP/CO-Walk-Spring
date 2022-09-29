package com.omega.cowalk.controller;

import com.omega.cowalk.domain.dto.WalkHistoryResponseDto;
import com.omega.cowalk.security.auth.PrincipalUserDetails;
import com.omega.cowalk.service.WalkHistoryService;
import com.omega.cowalk.util.SuccessResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/walk-history")
@RequiredArgsConstructor
public class WalkHistoryController {

    private final WalkHistoryService walkHistoryService;

    // 특정 월에 해당하는 발걸음 히스토리 조회 -> 디폴트 값은 현재 달임
    @GetMapping
    public ResponseEntity<SuccessResult> getWalkHistories(
            @RequestParam(name = "month", required = false) String month,
            @AuthenticationPrincipal PrincipalUserDetails principalUserDetails){

        String unboxedMonth = month.isBlank() ? LocalDate.now().getMonth().toString() : month;
        List<WalkHistoryResponseDto> walkHistoryResponseDtos =
                walkHistoryService.findByUserId(unboxedMonth, principalUserDetails.getUser().getId());

        log.debug("발걸음 히스토리 조회");
        return ResponseEntity.ok(new SuccessResult(walkHistoryResponseDtos));
    }

    // 발걸음 히스토리 추가 -> 비동기 고려
    @PostMapping
    public ResponseEntity<Object> addWalkHistory(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails){

        walkHistoryService.addWalkHistory(principalUserDetails.getUser().getId());

        log.debug(String.format("발걸음 히스토리 추가 userId=%s, walkDate=%s"
                        , principalUserDetails.getUser().getId(), LocalDate.now()));
        return ResponseEntity.ok().build();
    }
}
