package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.playlist.PlayList;
import com.omega.cowalk.domain.entity.playlist.PlayListPrimaryKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaylistRepository extends CrudRepository<PlayList, PlayListPrimaryKey> {

    @Query("select p from PlayList p where p.user_id = ?1")
    public List<PlayList> findPlayListsByUser_id(long user_id);
}
