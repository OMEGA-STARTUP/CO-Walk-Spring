package com.omega.cowalk.domain.entity.user;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@ToString
@Table(name="users")
@Builder
public class User
{
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identifier")
    private final String identifier;

    @Column(name = "password")
    private final String password;

    @Column(name = "email")
    private final String email;

    @Column(name = "nickname")
    private final String nickname;

    @Column(name = "role")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private final Role role = Role.USER;

    @Column(name = "sound_background_img_url")
    private final String soundBackgroundImgUrl;

    @Column(name = "profile_img_url")
    private final String profileImgUrl;

    //유저의 보유 소리수
    @Formula("(select COUNT(*) from playlist where user_id=user_id)")
    private final int soundNumb;


}
