package com.omega.cowalk.repository;

import com.omega.cowalk.domain.entity.favorite.Favorite;
import com.omega.cowalk.domain.entity.favorite.FavoritePrimaryKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FavoriteRepository extends CrudRepository<Favorite, FavoritePrimaryKey> {

    @Query("select f from Favorite f where f.userId = :userId")
    List<Favorite> findFavoritesByUserId(long userId);
}
