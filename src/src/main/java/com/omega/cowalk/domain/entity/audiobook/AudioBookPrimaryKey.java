package com.omega.cowalk.domain.entity.audiobook;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class AudioBookPrimaryKey implements Serializable
{
    private String audioBookTitle;

    private long userId;

}
