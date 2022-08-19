package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByIdentifier(String identifier);

    @Query("SELECT CASE WHEN (COUNT(*) >= 1) THEN false ELSE true END FROM User u WHERE u.nickname = ?1")
    public boolean isNotDuplicateNickname(String nickname);

    @Query("SELECT CASE WHEN (COUNT(*) >= 1) THEN false ELSE true END FROM User u WHERE u.identifier = ?1")
    public boolean isNotDuplicateIdentifier(String identifier);
}
