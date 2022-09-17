package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.soundhistory.SoundHistory;
import com.omega.cowalk.domain.entity.soundhistory.SoundHistoryPrimaryKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SoundHistoryRepository extends CrudRepository<SoundHistory, SoundHistoryPrimaryKey> {

    @Query(value = "SELECT * FROM user_recent_sound_history WHERE user_id = :userId", nativeQuery = true)
    Page<SoundHistory> getCurrentSoundHistory(@Param("userId") Long userId, Pageable pages);
}
