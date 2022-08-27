package com.omega.cowalk;

import com.amazonaws.util.IOUtils;
import com.omega.cowalk.domain.dto.GetUserProfileResponseDto.GetUserProfileResponseDto;
import com.omega.cowalk.domain.entity.backgroundsound.BackgroundSound;
import com.omega.cowalk.domain.entity.playlist.PlayList;
import com.omega.cowalk.domain.entity.soundhistory.SoundHistory;
import com.omega.cowalk.repository.BackgroundSoundRepository;
import com.omega.cowalk.repository.PlaylistRepository;
import com.omega.cowalk.repository.SoundHistoryRepository;
import com.omega.cowalk.service.UserService;
import com.omega.cowalk.util.MailSender;
import com.omega.cowalk.util.PictureUploader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@SpringBootTest
@Slf4j
class CowalkApplicationTests {

    @Test
    void contextLoads() {
    }
















}
