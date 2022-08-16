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
    private final long sound_id;

    @Column(name="sound_name")
    private final String sound_name;

    @Column(name="sound_play_time")
    private final int sound_play_time;

    @Column(name="sound_img_url")
    private final String sound_img_url;

    @Column(name="sound_play_url")
    private final String sound_play_url;

    @Column(name="sound_src_url")
    private final String sound_src_url;


    @Type(type = "jsonb")
    @Column(name="stepping_sounds" , columnDefinition = "jsonb")
    private final List<SteppingSound> stepping_sounds;

}
