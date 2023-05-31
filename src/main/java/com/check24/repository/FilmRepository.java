package com.check24.repository;

import com.check24.entity.FilmEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmRepository extends CrudRepository<FilmEntity, Long> {

    public static final String PUT_AVERAGE_RATING_QUERY =
            "UPDATE films SET averageRating = :averageRating, voitedCount = :voitedCount WHERE ID = ?filmId";

    FilmEntity findByFilmName(String name);

    List<FilmEntity> findAll();

    @Modifying
    @Query(value = PUT_AVERAGE_RATING_QUERY, nativeQuery = true)
    void putRating(@Param("filmId") Long filmId,
                   @Param("averageRating") Double averageRating,
                   @Param("voitedCount") Integer voitedCount);


}
