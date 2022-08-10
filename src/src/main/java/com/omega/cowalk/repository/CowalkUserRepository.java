package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.CowalkUser;
import org.springframework.data.repository.CrudRepository;


public interface CowalkUserRepository extends CrudRepository<CowalkUser, Long>
{

    CowalkUser findByIdentifier(String identifier);
}
