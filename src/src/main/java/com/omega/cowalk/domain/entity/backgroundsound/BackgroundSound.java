package com.omega.cowalk.domain.entity.backgroundsound;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@ToString
@Table(name="background_sound")
@Builder
@TypeDef(
        name= "jsonb",
        typeClass = JsonBinaryType.class
)
public class BackgroundSound
{
    @Id
    @Column(name="sound_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long soundId;

    @Column(name="sound_name")
    private final String soundName;

    @Column(name="sound_play_time")
    private final int soundPlayTime;

    @Column(name="sound_img_url")
    private final String soundImgUrl;

    @Column(name="sound_play_url")
    private final String soundPlayUrl;

    @Column(name="sound_src_url")
    private final String soundSrcUrl;


    @Type(type = "jsonb")
    @Column(name="stepping_sounds" , columnDefinition = "jsonb")
    private final List<SteppingSound> steppingSounds;

}
