package com.check24.service;

import com.check24.domain.Film;
import com.check24.entity.FilmEntity;
import com.check24.enums.Genres;
import com.check24.exceptions.FilmAbsentException;
import com.check24.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private static final Integer NOBODY_VOTED = 0;

    private final FilmRepository filmRepository;

    @Autowired
    FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Cacheable
    public List<Film> allFilms() {
        return filmRepository.findAllByOrderByIdAsc().stream()
                .map(filmEntity -> new Film(
                        filmEntity.getId(),
                        filmEntity.getFilmName(),
                        filmEntity.getAverageRating(),
                        filmEntity.getGenre(),
                        filmEntity.getDirector(),
                        filmEntity.getVotedCount()
                )).collect(Collectors.toList());
    }

    public Film searchByName(String filmName) {
        FilmEntity filmEntity = filmRepository.findByFilmName(filmName);
        return new Film(
                filmEntity.getId(),
                filmEntity.getFilmName(),
                filmEntity.getAverageRating(),
                filmEntity.getGenre(),
                filmEntity.getDirector(),
                filmEntity.getVotedCount());
    }

    public void addNewFilm(String filmName,
                           Double averageRating,
                           Genres genre,
                           String director) {
        filmRepository.save(
                new FilmEntity(
                        filmName,
                        averageRating,
                        genre,
                        director,
                        NOBODY_VOTED
                )
        );
    }

    @Transactional
    public void putRating(Long filmId, Integer ratingValue) throws FilmAbsentException {
        Optional<FilmEntity> film = filmRepository.findById(filmId);

        if (film.isPresent()) {

            FilmEntity obj = film.get();
            Integer voitedCount = obj.getVotedCount();
            Double averageRatingCalc = (obj.getAverageRating() * voitedCount
                    + ratingValue) / ++voitedCount;

            filmRepository.putFilmRate(filmId, averageRatingCalc, voitedCount);
        } else {
            throw new FilmAbsentException("Film is absent");
        }
    }

    public List<Film> recommendedFilms(Integer userId, Integer filmId, Integer ratingValue) {
        // find all related grouped films by genre and high rate for the userId
        return null;
    }

}
