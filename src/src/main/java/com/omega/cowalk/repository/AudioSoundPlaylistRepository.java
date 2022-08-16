package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.audiosoundplaylist.AudioSoundPlaylist;
import com.omega.cowalk.domain.entity.audiosoundplaylist.AudioSoundPlaylistPrimaryKey;
import org.springframework.data.repository.CrudRepository;

public interface AudioSoundPlaylistRepository extends CrudRepository<AudioSoundPlaylist, AudioSoundPlaylistPrimaryKey>
{
}
