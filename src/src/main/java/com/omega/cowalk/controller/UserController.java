package com.omega.cowalk.controller;

import com.omega.cowalk.domain.dto.*;
import com.omega.cowalk.domain.dto.CheckCodeForPasswordResponseDto.CheckCodeForPasswordResponseDto;
import com.omega.cowalk.domain.dto.GetUserProfileResponseDto.GetUserProfileResponseDto;
import com.omega.cowalk.domain.dto.SendCodeForPasswordResponseDto.Data;
import com.omega.cowalk.domain.dto.SendCodeForPasswordResponseDto.SendCodeForPasswordResponseDto;
import com.omega.cowalk.domain.entity.user.User;
import com.omega.cowalk.exception.IdentifierDuplicateException;
import com.omega.cowalk.exception.NicknameDuplicationException;
import com.omega.cowalk.exception.UserNotFoundException;
import com.omega.cowalk.security.auth.PrincipalUserDetails;
import com.omega.cowalk.security.token.service.TokenService;
import com.omega.cowalk.service.UserService;
import com.omega.cowalk.util.MailSender;
import com.omega.cowalk.util.PictureUploader;
import com.omega.cowalk.util.RandomCodeGenerator;
import com.omega.cowalk.util.SuccessResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final MailSender mailSender;
    private final TokenService tokenService;
    private final UserService userService;
    private final PictureUploader pictureUploader;

    @PostMapping("/register/email/send")
    public ResponseEntity<SuccessResult> sendEmailCode(@Valid @RequestBody RegisterSendCodeRequestDto registerSendCodeRequestDto){
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
        RegisterSendCodeResponseDto registerSendCodeResponseDto =
                new RegisterSendCodeResponseDto(token);

        return ResponseEntity.ok(new SuccessResult(registerSendCodeResponseDto));

    }

    @PostMapping("/register/email/check")
    public ResponseEntity<SuccessResult> checkCode(@Valid @RequestBody RegisterCheckCodeRequestDto registerCheckCodeRequestDto)
    {
        String sendCodeToken = registerCheckCodeRequestDto.getJwtToken();
        String access_code = registerCheckCodeRequestDto.getUserAccessCode();

        String email = tokenService.verifySignUpEmailSendCodeToken(sendCodeToken, access_code);

        String verifiedCodeToken = tokenService.issueSignUpEmailVerifyCodeToken(email);
        RegisterCheckCodeResponseDto registerCheckCodeResponseDto =
                new RegisterCheckCodeResponseDto(verifiedCodeToken);

        return ResponseEntity.ok(new SuccessResult(registerCheckCodeResponseDto));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto)
    {
        String token = registerRequestDto.getJwtToken();
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

    @PatchMapping("/profile/nickname")
    public void nicknamePatch(@Valid @RequestBody NicknamePatchRequestDto nicknamePatchRequestDto,
                              Authentication authentication)
    {
        if(!userService.isNotDuplicateNickname(nicknamePatchRequestDto.getNickname()))
        {
            throw new NicknameDuplicationException(nicknamePatchRequestDto.getNickname() + " is a duplicate nickname");
        }

        PrincipalUserDetails principalUserDetails = (PrincipalUserDetails) authentication.getPrincipal();

        userService.updateNickname(nicknamePatchRequestDto.getNickname(), principalUserDetails.getUser().getId());


    }


    @PutMapping("/profile/user-picture")
    public void profileUpdate(@RequestPart MultipartFile profile_picture ,
                              Authentication authentication) throws IOException
    {
        long userId = ((PrincipalUserDetails) authentication.getPrincipal()).getUser().getId();
        pictureUploader.uploadProfilePicture(profile_picture, userId)
                .whenComplete((result, ex) ->{
                    if(ex == null && result != null){
                        log.debug("successfully uploaded picture!");
                        userService.updateProfilePicture(result, userId);
                    }
                    else if(ex != null)
                    {
                        log.error("error in uploading picture");
                        log.error(ex.getMessage());
                    }
                    else{
                        log.error("error not generated but failed. something went wrong");
                    }
                });

    }

    @PutMapping("/profile/sound-picture")
    public void SoundPictureUpdate(@Valid @RequestBody SoundPictureUpdateRequestDto soundPictureUpdateRequestDto,
                                   Authentication authentication)
    {
        long userId = ((PrincipalUserDetails) authentication.getPrincipal()).getUser().getId();
        userService.updateSoundPicture(soundPictureUpdateRequestDto.getSoundBackgroundId(), userId);
    }

    @GetMapping("/profile")
    public GetUserProfileResponseDto getUserProfile(Authentication authentication)
    {
        long userId = ((PrincipalUserDetails) authentication.getPrincipal()).getUser().getId();

        //page 값을 어떻게 받을지 몰라서 임의로 정했습니다.
        Pageable pageable = PageRequest.of(0, 10);
        return userService.getRecentSoundHistory(userId, pageable);
    }

    @PostMapping("/id-inquiry/email/send")
    public void sendIdToEmail(@Valid @RequestBody SendIdToEmailRequestDto sendIdToEmailRequestDto)
    {
        String email = sendIdToEmailRequestDto.getEmail();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email + ": no user found with matching email"));
        mailSender.sendMailForIdInquiry(email, user.getIdentifier())
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
                });;

        return;
    }

    @PostMapping("/pw-inquiry/email/send")
    public ResponseEntity<SendCodeForPasswordResponseDto> sendCodeForPassword
            ( @Valid @RequestBody SendCodeForPasswordRequestDto sendCodeForPasswordRequestDto)
    {
        String email = sendCodeForPasswordRequestDto.getEmail();
        String identifier = sendCodeForPasswordRequestDto.getIdentifier();

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email + ": no user found with matching email"));
        if(!user.getIdentifier().equals(identifier))
        {
            throw new UserNotFoundException(identifier +  " : no user found with matching identifier");
        }

        String code = RandomCodeGenerator.generateRandomCode(6);
        mailSender.senMailForPassword(email, code);

        String jwt_token = tokenService.issuePasswordEmailSendCodeToken(identifier, code);

        return ResponseEntity.ok(new SendCodeForPasswordResponseDto(new Data(jwt_token)));


    }

    @PostMapping("/pw-inquiry/email/check")
    public ResponseEntity<CheckCodeForPasswordResponseDto> checkCodeForPassword
            (@Valid @RequestBody CheckCodeForPasswordRequestDto checkCodeForPasswordRequestDto)
    {
        String user_access_code = checkCodeForPasswordRequestDto.getUserAccessCode();
        String old_jwt_token = checkCodeForPasswordRequestDto.getJwtToken();

        String identifier = tokenService.verifyPasswordEmailSendCodeToken(old_jwt_token, user_access_code);

        String new_jwt_token = tokenService.issuePasswordEmailVerifyToken(identifier);

        return ResponseEntity.ok(new CheckCodeForPasswordResponseDto(new Data(new_jwt_token)));
    }

    @PutMapping("/password")
    public void updatePassword(@Valid @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto)
    {
        String identifier = updatePasswordRequestDto.getIdentifier();
        String password = updatePasswordRequestDto.getPassword();
        String jwt_token = updatePasswordRequestDto.getJwt_token();

        String verifiedIdentifier = tokenService.verifyPasswordEmailCheckCodeToken(jwt_token, identifier);

        userService.updatePassword(password, verifiedIdentifier);

        return;
    }






}