package com.omega.cowalk;

import com.omega.cowalk.domain.dto.RegisterRequestDto;
import com.omega.cowalk.domain.entity.User;
import com.omega.cowalk.domain.entity.Role;
import com.omega.cowalk.domain.entity.attendance.Attendance;
import com.omega.cowalk.domain.entity.audiobook.AudioBook;
import com.omega.cowalk.domain.entity.audiosoundplaylist.AudioSoundPlaylist;
import com.omega.cowalk.domain.entity.backgroundsound.BackgroundSound;
import com.omega.cowalk.domain.entity.backgroundsound.SteppingSound;
import com.omega.cowalk.domain.entity.favorite.Favorite;
import com.omega.cowalk.domain.entity.notification.Notification;
import com.omega.cowalk.domain.entity.notificationread.NotificationRead;
import com.omega.cowalk.domain.entity.playlist.PlayList;
import com.omega.cowalk.domain.entity.soundhistory.SoundHistory;
import com.omega.cowalk.repository.*;
import com.omega.cowalk.security.auth.PrincipalUserDetailsService;
import com.omega.cowalk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@Slf4j
class CowalkApplicationTests {

    @Test
    void contextLoads() {
    }





}
