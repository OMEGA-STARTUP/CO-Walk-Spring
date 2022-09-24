package com.omega.cowalk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
@Getter
public class NicknamePatchRequestDto
{
    @NotNull
    private final String nickname;

}
