package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.soundhistory.SoundHistory;
import com.omega.cowalk.domain.entity.soundhistory.SoundHistoryPrimaryKey;
import org.springframework.data.repository.CrudRepository;

public interface SoundHistoryRepository extends CrudRepository<SoundHistory, SoundHistoryPrimaryKey> {
}
