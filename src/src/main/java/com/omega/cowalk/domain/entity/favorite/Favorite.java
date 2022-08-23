package com.omega.cowalk.domain.entity.favorite;

import com.omega.cowalk.domain.entity.backgroundsound.BackgroundSound;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
@ToString
@Table(name="favorite")
@IdClass(FavoritePrimaryKey.class)
public class Favorite
{

    @Id
    @Column(name = "user_id")
    private final long userId;

    @Id
    @Column(name = "sound_id")
    private final long soundId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sound_id",  referencedColumnName = "sound_id", updatable = false, insertable = false)
    private final BackgroundSound backgroundSound;

}
