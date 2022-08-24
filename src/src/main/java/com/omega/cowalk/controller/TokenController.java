package com.omega.cowalk.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.omega.cowalk.domain.entity.user.User;
import com.omega.cowalk.security.auth.PrincipalUserDetails;
import com.omega.cowalk.security.token.dto.JwtTokenReIssueDto;
import com.omega.cowalk.security.token.service.TokenService;
import com.omega.cowalk.service.UserService;
import com.omega.cowalk.util.SuccessResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;
    private final UserService userService;

    @GetMapping("/re-issue")
    public ResponseEntity<SuccessResult> reIssueAccessToken(
            @RequestHeader(name = "REFRESH_TOKEN") String jwtTokenHeader) throws JWTVerificationException {
        log.debug("request reached to the controller");

        String identifier = tokenService.verifyToken(jwtTokenHeader, tokenService.getREFRESH_KEY());

        User user = userService.findByIdentifier(identifier)
                .orElseThrow(() -> new UsernameNotFoundException("Not found username"));

        String jwtAccessToken = tokenService.reIssue(new PrincipalUserDetails(user));
        JwtTokenReIssueDto jwtTokenReIssueDto = new JwtTokenReIssueDto(jwtAccessToken);

        return ResponseEntity.ok()
                .body(new SuccessResult(jwtTokenReIssueDto));
    }



}
