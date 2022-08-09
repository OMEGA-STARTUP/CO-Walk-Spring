package com.omega.cowalk.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name="users")
public class CowalkUser
{
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    @Column(name = "identifier")
    @NonNull
    private String identifier;

    @Column(name = "password")
    @NonNull
    private String password;

    @Column(name = "email")
    @NonNull
    private String email;

    @Column(name = "nickname")
    @NonNull
    private String nickname;

    @Column(name = "role")
    @NonNull
    private String role;

    @Column(name = "sound_background_img_url")
    private String sound_background_img_url;

    @Column(name = "profile_img_url")
    private String profile_img_url;

}
