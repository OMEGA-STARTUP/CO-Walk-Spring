package com.omega.cowalk.service;

import com.omega.cowalk.domain.CowalkUser;
import com.omega.cowalk.repository.CowalkUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
public class UserService {

    @Autowired
    CowalkUserRepository cowalkUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public CowalkUser createUser(CowalkUser cowalkUser){
        return cowalkUserRepository.save(cowalkUser);
    }

    public CowalkUser findByIdentifier(String identifier)
    {
        return cowalkUserRepository.findByIdentifier(identifier);
    }

    @Transactional
    public void deleteUser(CowalkUser cowalkUser)
    {
        cowalkUserRepository.delete(cowalkUser);
    }



}
