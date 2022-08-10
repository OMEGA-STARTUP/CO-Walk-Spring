package com.omega.cowalk.service;

import com.omega.cowalk.domain.dto.CowalkUserDto;
import com.omega.cowalk.domain.entity.CowalkUser;
import com.omega.cowalk.domain.entity.Role;
import com.omega.cowalk.repository.CowalkUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    CowalkUserRepository cowalkUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public Optional<CowalkUser> createUser(CowalkUserDto cowalkUserDto){
        CowalkUser cowalkUser = CowalkUser.builder()
                .identifier(cowalkUserDto.getIdentifier())
                .password(passwordEncoder.encode(cowalkUserDto.getPassword()))
                .email(cowalkUserDto.getEmail())
                .nickname(cowalkUserDto.getNickname())
                .role(cowalkUserDto.getRole() == null? Role.ROLE_USER: cowalkUserDto.getRole())
                .profile_img_url(cowalkUserDto.getProfile_img_url())
                .sound_background_img_url(cowalkUserDto.getSound_background_img_url())
                .build();

        return Optional.of(cowalkUserRepository.save(cowalkUser));
    }

    public Optional<CowalkUser> findByIdentifier(String identifier)
    {
        return Optional.of(cowalkUserRepository.findByIdentifier(identifier));
    }

    @Transactional
    public void deleteUser(CowalkUser cowalkUser)
    {
        cowalkUserRepository.delete(cowalkUser);
    }



}
