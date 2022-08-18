package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByIdentifier(String identifier);
}
