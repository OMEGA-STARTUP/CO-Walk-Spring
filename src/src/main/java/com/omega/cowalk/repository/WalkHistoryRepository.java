package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.walkhistory.WalkHistory;
import com.omega.cowalk.domain.entity.walkhistory.WalkHistoryPrimaryKey;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WalkHistoryRepository extends MongoRepository<WalkHistory, WalkHistoryPrimaryKey> {
}
