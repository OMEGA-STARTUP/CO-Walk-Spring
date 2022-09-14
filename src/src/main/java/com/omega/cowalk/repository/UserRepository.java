package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByIdentifier(String identifier);

    Optional<User> findByEmail(String email);

    @Query("SELECT CASE WHEN (COUNT(*) >= 1) THEN false ELSE true END FROM User u WHERE u.nickname = :nickname")
    public boolean isNotDuplicateNickname(@Param("nickname")String nickname);

    @Query("SELECT CASE WHEN (COUNT(*) >= 1) THEN false ELSE true END FROM User u WHERE u.identifier = :identifier")
    public boolean isNotDuplicateIdentifier(@Param("identifier") String identifier);

    @Query("SELECT CASE WHEN (COUNT(*) >= 1) THEN false ELSE true END FROM User u WHERE u.email = :email")
    public boolean isNotDuplicateEmail(@Param("email") String email);

    @Query("UPDATE User u SET u.nickname = :nickname WHERE u.id = :userId")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    public void updateNickname(@Param("nickname") String nickname, @Param("userId") Long userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE User u SET u.profileImgUrl = :profileImgUrl WHERE u.id = :userId")
    public void updateProfileImgUrl( @Param("userId") Long userId, @Param("profileImgUrl") String profileImgUrl );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE User u SET u.soundBackgroundId = :soundBackgroundId WHERE u.id = :userId")
    public void updateSoundImgUrl(@Param("soundBackgroundId") long soundBackgroundId, @Param("userId") Long userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE User u SET u.password = :password WHERE u.identifier = :identifier")
    public void updateUserPassword(@Param("password") String password, @Param("identifier") String identifier);


}
