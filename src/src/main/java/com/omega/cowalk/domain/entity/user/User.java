package com.omega.cowalk.domain.entity.user;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@Table(name="users")
@Builder
@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
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

    @Column(name = "profile_background_id")
    private final long profileBackgroundId;

    @Column(name = "profile_img_url")
    private final String profileImgUrl;

}
