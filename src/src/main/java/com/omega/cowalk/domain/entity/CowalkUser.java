package com.omega.cowalk.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@ToString
@Table(name="users")
@Builder
public class CowalkUser
{
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

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
    private final Role role = Role.ROLE_USER;

    @Column(name = "sound_background_img_url")
    private final String sound_background_img_url;

    @Column(name = "profile_img_url")
    private final String profile_img_url;

}
