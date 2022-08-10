package com.omega.cowalk.security;

import com.omega.cowalk.domain.entity.CowalkUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CowalkUserDetails implements UserDetails {
    @NonNull
    private Long user_id;

    @NonNull
    private String password;

    @NonNull
    private String identifier;

    private Collection<SimpleGrantedAuthority> role;

    public CowalkUserDetails(CowalkUser cowalkUser)
    {
        this.user_id = cowalkUser.getUser_id();
        this.password = cowalkUser.getPassword();
        this.identifier = cowalkUser.getIdentifier();
        this.role = new ArrayList<SimpleGrantedAuthority>();
        role.add(new SimpleGrantedAuthority(cowalkUser.getRole().toString()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return identifier;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
