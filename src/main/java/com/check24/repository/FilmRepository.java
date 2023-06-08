package com.check24.repository;

import com.check24.entity.FilmEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilmRepository extends CrudRepository<FilmEntity, Long> {

    FilmEntity findByFilmName(String name);

    List<FilmEntity> findAllByOrderByIdAsc();
}
