package com.omega.cowalk.service;

import com.omega.cowalk.domain.dto.RegisterRequestDto;
import com.omega.cowalk.domain.entity.User;
import com.omega.cowalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Optional<User> createUser(RegisterRequestDto registerRequestDto){
        User user = User.builder()
                .identifier(registerRequestDto.getIdentifier())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .email(registerRequestDto.getEmail())
                .nickname(registerRequestDto.getNickname())
                .build();

        return Optional.of(userRepository.save(user));
    }

    public Optional<User> findByIdentifier(String identifier) {
        return userRepository.findByIdentifier(identifier);
    }

    @Transactional
    public void deleteUser(User user)
    {
        userRepository.delete(user);
    }

    public boolean isNotDuplicateNickname( String nickname)
    {
        return userRepository.isNotDuplicateNickname(nickname);
    }

    public boolean isNotDuplicateIdentifier(String identifier)
    {
        return userRepository.isNotDuplicateIdentifier(identifier);
    }



}
