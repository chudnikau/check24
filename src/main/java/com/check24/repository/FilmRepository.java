package com.check24.repository;

import com.check24.entity.FilmEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmRepository extends CrudRepository<FilmEntity, Long> {

    FilmEntity findByFilmName(String name);

    List<FilmEntity> findAllByOrderByIdAsc();

    @Modifying
    @Query(value = "update FilmEntity set averageRating = :averageRating, votedCount = :votedCount where id = :filmId")
    void putFilmRate(@Param("filmId") Long filmId,
                     @Param("averageRating") Double averageRating,
                     @Param("votedCount") Integer votedCount);


}
