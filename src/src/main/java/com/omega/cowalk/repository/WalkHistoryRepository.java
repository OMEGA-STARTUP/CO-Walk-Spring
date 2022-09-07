package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.walkhistory.WalkHistory;
import com.omega.cowalk.domain.entity.walkhistory.WalkHistoryPrimaryKey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WalkHistoryRepository extends MongoRepository<WalkHistory, WalkHistoryPrimaryKey> {

    @Query(value = "{walkDate: ?0, userId: ?1}")
    Optional<WalkHistory> findByWalkDateAndUserId(LocalDate walkDate, long userId);

    @Query("{walkDate: { $gte: ?0, $lte: ?1 }, userId: ?2}")
    List<WalkHistory> findByUserId(LocalDate from, LocalDate to, long userId);

    @Query(value = "{walkDate: ?0, userId: ?1}")
    @Update(value = "{ '$inc' : {'steps': 1}}")
    void incrementStepsByWalkDateAndUserId(LocalDate walkDate, long userId);
}
