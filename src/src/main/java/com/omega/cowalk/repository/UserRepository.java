package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByIdentifier(@Param("identifier") String identifier);
    Optional<User> findByEmail(@Param("email") String email);

    Boolean existsByNickname(@Param("nickname") String nickname);
    Boolean existsByIdentifier(@Param("identifier") String identifier);
    Boolean existsByEmail(@Param("email") String email);

    @Query("UPDATE User u SET u.nickname = :nickname WHERE u.id = :userId")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void updateNickname(@Param("nickname") String nickname, @Param("userId") Long userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE User u SET u.profileImgUrl = :profileImgUrl WHERE u.id = :userId")
    void updateProfileImgUrl( @Param("userId") Long userId, @Param("profileImgUrl") String profileImgUrl );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE User u SET u.profileBackgroundId = :profileBackgroundId WHERE u.id = :userId")
    void updateSoundImgUrl(@Param("profileBackgroundId") long profileBackgroundId, @Param("userId") Long userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE User u SET u.password = :password WHERE u.identifier = :identifier")
    void updateUserPassword(@Param("password") String password, @Param("identifier") String identifier);

}
