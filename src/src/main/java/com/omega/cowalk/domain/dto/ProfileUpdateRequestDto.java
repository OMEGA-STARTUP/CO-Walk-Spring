package com.omega.cowalk.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class ProfileUpdateRequestDto {

    @NotNull
    private final MultipartFile profilePicture;


}
