package com.omega.cowalk.domain.entity.favorite;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class FavoritePrimaryKey implements Serializable {

    private long userId;

    private long soundId;

}
