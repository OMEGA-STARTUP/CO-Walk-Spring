package com.omega.cowalk.domain.entity.favorite;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class FavoritePrimaryKey implements Serializable {

    private long user_id;

    private long sound_id;

}
