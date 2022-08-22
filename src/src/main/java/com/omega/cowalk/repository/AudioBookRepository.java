package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.audiobook.AudioBook;
import com.omega.cowalk.domain.entity.audiobook.AudioBookPrimaryKey;
import com.omega.cowalk.domain.entity.backgroundsound.BackgroundSound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AudioBookRepository extends CrudRepository<AudioBook, AudioBookPrimaryKey> {

    //특정한 audiobook의 백그라운드 사운드를 다 가져오기
    //order_numb가 높으면 위에 놓을건지 낮으면 위에넣을건지 정해야 함 그거에 따라서 뒤에 DESC를 붙이던가 아니면 그대로 할지 정함.

    @Query("select bs from AudioBook a, AudioSoundPlaylist asp, BackgroundSound bs " +
            "where a.audioBookTitle = asp.audioBookTitle AND a.userId = asp.userId AND asp.soundId = bs.soundId " +
            "AND a.audioBookTitle = :audioBookTitle AND a.userId = :userId ORDER BY asp.orderNumb")
    Page<List<BackgroundSound>> getBackgroundSounds(String audioBookTitle, long userId, Pageable pageable);


}