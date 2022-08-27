package com.omega.cowalk.service;

import com.omega.cowalk.domain.dto.GetUserProfileResponseDto.GetUserProfileResponseDto;
import com.omega.cowalk.domain.dto.GetUserProfileResponseDto.UserRecentHistory;
import com.omega.cowalk.domain.dto.RegisterRequestDto;
import com.omega.cowalk.domain.entity.soundhistory.SoundHistory;
import com.omega.cowalk.domain.entity.user.User;
import com.omega.cowalk.repository.SoundHistoryRepository;
import com.omega.cowalk.repository.UserRepository;
import com.omega.cowalk.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SoundHistoryRepository soundHistoryRepository;

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

    public boolean isNotDuplicateEmail(String email)
    {
        return userRepository.isNotDuplicateEmail(email);
    }


    @Transactional
    public void updateNickname(String nickname, long userId)
    {
        userRepository.updateNickname(nickname, userId);
    }

    @Transactional
    public void updateProfilePicture(String path, long userId){
        userRepository.updateProfileImgUrl(userId, path);
    }

    @Transactional
    public void updateSoundPicture(long soundBackgroundId, long userId){
        userRepository.updateSoundImgUrl(soundBackgroundId, userId);
    }

    @Transactional(readOnly = true)
    public GetUserProfileResponseDto getRecentSoundHistory(long userId, Pageable pageable)
    {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user with id " + String.valueOf(userId) + " not found" ));

        List<SoundHistory> soundHistoryList = soundHistoryRepository.getCurrentSoundHistory(userId, pageable).getContent();

        List<UserRecentHistory> userRecentHistories =
                soundHistoryList.stream().map((soundHistory -> new UserRecentHistory(soundHistory))).collect(Collectors.toList());

        return new GetUserProfileResponseDto(user.getNickname(), user.getProfileImgUrl(), user.getSoundNumb(),
                user.getSoundBackgroundId(), userRecentHistories);

    }


}
