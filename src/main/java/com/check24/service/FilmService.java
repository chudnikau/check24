package com.check24.service;

import com.check24.domain.Film;
import com.check24.domain.User;
import com.check24.entity.FilmEntity;
import com.check24.entity.UserEntity;
import com.check24.enums.Genres;
import com.check24.repository.FilmRepository;
import com.check24.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private static final Integer NOBODY_VOTED = 0;

    private final FilmRepository filmRepository;

    private final UserRepository userRepository;

    private final User currUser;

    @Autowired
    FilmService(FilmRepository filmRepository,
                UserRepository userRepository,
                User currUser) {
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
        this.currUser = currUser;
    }

    public List<Film> allVoteAvailableFilms() {
        return filmRepository.findAllByOrderByIdAsc().stream()
                .map(film -> new Film(
                        film.getId(),
                        film.getFilmName(),
                        film.getAverageRating(),
                        film.getGenre(),
                        film.getDirector(),
                        film.getVotedCount(),
                        isUserVoted(film, currUser.getUserId())
                )).collect(Collectors.toList());
    }

    public List<Film> searchByName(String filmName) {
        return filmRepository.findAllByOrderByIdAsc().stream()
                .map(film -> new Film(
                        film.getId(),
                        film.getFilmName(),
                        film.getAverageRating(),
                        film.getGenre(),
                        film.getDirector(),
                        film.getVotedCount(),
                        isUserVoted(film, currUser.getUserId())
                ))
                .filter(
                        film -> film.getFilmName().toUpperCase().contains(filmName))
                .collect(Collectors.toList());
    }

    public void addNewFilm(User user,
                           String filmName,
                           Double averageRating,
                           Genres genre,
                           String director) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(user.getUserName());

        filmRepository.save(
                new FilmEntity(
                        Set.of(userEntity),
                        filmName,
                        averageRating,
                        genre,
                        director,
                        NOBODY_VOTED
                )
        );
    }

    @Transactional
    public void putRating(Long userId, Long filmId, Integer ratingValue) {

        filmRepository.findById(filmId).ifPresent(
                film -> userRepository.findById(userId).ifPresent(
                        user -> {
                            if (!isUserVoted(film, user.getId())) {
                                saveUserVote(film, user, ratingValue);
                            }
                        }
                )
        );

    }

    private void saveUserVote(FilmEntity film, UserEntity user, Integer ratingValue) {
        Integer votedCount = film.getVotedCount();

        Double newAvrRate = calcNewRate(
                film.getAverageRating(),
                votedCount,
                ratingValue);

        film.getUserEntity().add(user);
        film.setAverageRating(newAvrRate);
        film.setVotedCount(votedCount + 1);

        filmRepository.save(film);
    }

    private boolean isUserVoted(FilmEntity film, Long userId) {

        return Optional.ofNullable(film.getUserEntity()).filter(
                userEntities ->
                        userEntities.stream().anyMatch(user -> user.getId().equals(userId))
        ).isPresent();
    }

    private Double calcNewRate(Double currAvrRate, Integer votedCount, Integer newRate) {
        return (currAvrRate * votedCount + newRate) / (votedCount + 1);
    }

    public List<Film> recommendedFilms(Integer userId, Integer filmId, Integer ratingValue) {
        // find all related grouped films by genre and high rate for the userId
        return null;
    }

}
