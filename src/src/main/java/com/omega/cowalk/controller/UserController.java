package com.omega.cowalk.controller;

import com.omega.cowalk.domain.dto.*;
import com.omega.cowalk.exception.IdentifierDuplicateException;
import com.omega.cowalk.exception.NicknameDuplicationException;
import com.omega.cowalk.security.token.service.TokenService;
import com.omega.cowalk.service.UserService;
import com.omega.cowalk.util.MailSender;
import com.omega.cowalk.util.RandomCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    MailSender mailSender;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;


    @PostMapping("/register/email/send")
    public ResponseEntity<RegisterSendCodeResponseDto> sendEmailCode(@Valid @RequestBody RegisterSendCodeRequestDto registerSendCodeRequestDto){
        log.debug("sendMailCode is called!");

        String email = registerSendCodeRequestDto.getEmail();

        String code = RandomCodeGenerator.generateRandomCode(6);

        mailSender.sendMailForSignup(email, code)
                .whenComplete((result, ex) ->{
                    if(ex == null && result){
                        log.debug("successfully sent email!");
                    }
                    else if(ex != null)
                    {
                        log.error("error in sending email!");
                        log.error(ex.getMessage());
                    }
                    else{
                        log.error("error not generatged but failed. something went wrong");
                    }
                });

        String token = tokenService.issueSignUpEmailSendCodeToken(email, code);

        return ResponseEntity.ok(new RegisterSendCodeResponseDto(token));

    }

    @PostMapping("/register/email/check")
    public ResponseEntity<RegisterCheckCodeResponseDto> checkCode(@Valid @RequestBody RegisterCheckCodeRequestDto registerCheckCodeRequestDto)
    {
        String sendCodeToken = registerCheckCodeRequestDto.getJwt_token();
        String access_code = registerCheckCodeRequestDto.getUser_access_code();

        String email = tokenService.verifySignUpEmailSendCodeToken(sendCodeToken, access_code);

        String verifiedCodeToken = tokenService.issueSignUpEmailVerifyCodeToken(email);

        return ResponseEntity.ok(new RegisterCheckCodeResponseDto(verifiedCodeToken));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto)
    {
        String token = registerRequestDto.getJwt_token();
        tokenService.verifySignUpEmailCheckCodeToken(token , registerRequestDto.getEmail());

        if(!userService.isNotDuplicateNickname(registerRequestDto.getNickname()))
        {
            throw new NicknameDuplicationException(registerRequestDto.getNickname() + " is a duplicate nickname");
        }

        if(!userService.isNotDuplicateIdentifier(registerRequestDto.getIdentifier()))
        {
            throw new IdentifierDuplicateException(registerRequestDto.getIdentifier() + " is a duplicate identifier");
        }

        if(!userService.isNotDuplicateEmail(registerRequestDto.getEmail()))
        {
            throw new NicknameDuplicationException(registerRequestDto.getEmail() + " is a duplicate email");
        }

        userService.createUser(registerRequestDto);



        return ResponseEntity.ok().build();
    }





}
