package com.check24.service;

import com.check24.domain.User;
import com.check24.entity.FilmEntity;
import com.check24.entity.UserEntity;
import com.check24.enums.Genres;
import com.check24.exceptions.FilmAbsentException;
import com.check24.exceptions.UserAdsentException;
import com.check24.repository.FilmRepository;
import com.check24.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest({"spring.main.allow-bean-definition-overriding=true"})
public class FilmServiceTest {

    @Autowired
    private FilmService filmService;

    @MockBean
    private FilmRepository filmRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private User currUser;

    private static final long FILM_ID = 1;
    private static final long USER_ID = 1;

    @Before
    public void setUp() {
        login();

        UserEntity generalUserEntity = new UserEntity(USER_ID, "Benedict");

        FilmEntity generalNotVotedUserFilmEntity = new FilmEntity(
                new HashSet<>(),
                "Mister bean",
                0d,
                Genres.Comedy,
                "John Howard Davies",
                0
        );

        generalNotVotedUserFilmEntity.setId(FILM_ID);

        when(filmRepository.findById(anyLong())).thenReturn(
                Optional.of(
                        generalNotVotedUserFilmEntity
                )
        );

        when(userRepository.findById(anyLong())).thenReturn(
                Optional.of(
                        generalUserEntity
                )
        );
    }

    private void login() {
        currUser.setUserId(100L);
    }

    @Test
    public void testPuttingRatting() throws FilmAbsentException, UserAdsentException {

        filmService.putRating(FILM_ID, 1);

        verify(filmRepository, atLeastOnce())
                .findById(anyLong());

        verify(userRepository, atLeastOnce())
                .findById(anyLong());

        verify(filmRepository, atLeastOnce()).save(any());
    }

    @Test
    public void testCalcSinglePuttingRatting() {

        UserEntity user = new UserEntity(100L, "Siarhei Chudnikau");

        when(userRepository.findById(anyLong())).thenReturn(
                Optional.of(
                        user
                )
        );

        FilmEntity film = new FilmEntity(
                new HashSet<>(),
                "Mister bean",
                0d,
                Genres.Comedy,
                "John Howard Davies",
                0
        );
        film.setId(200L);

        when(filmRepository.findById(200L)).thenReturn(
                Optional.of(
                        film
                )
        );

        filmService.putRating(200L, 5);
        film.getUserEntity().add(user);
        verify(filmRepository, atLeastOnce()).save(film);
    }

    @Test
    public void testCalcMultiplePuttingRatting() {

        UserEntity user = new UserEntity(100L, "Siarhei Chudnikau");

        when(userRepository.findById(anyLong())).thenReturn(
                Optional.of(
                        user
                )
        );

        int votedCount = 5;
        double averageRating = (double) (1 + 2 + 3 + 4 + 5) / votedCount;

        FilmEntity film = new FilmEntity(
                new HashSet<>(),
                "Mister bean",
                averageRating,
                Genres.Comedy,
                "John Howard Davies",
                votedCount
        );

        film.setId(200L);

        when(filmRepository.findById(200L)).thenReturn(
                Optional.of(
                        film
                )
        );

        int newRating = 3;
        int newVotedCount = votedCount + 1;

        filmService.putRating(200L, newRating);

        Integer expectedVotedCount = votedCount + 1;
        Double expectedAvrRating = (double) (1 + 2 + 3 + 4 + 5 + newRating) / newVotedCount;

        verify(filmRepository, atLeastOnce()).save(film);

        assertEquals(expectedAvrRating, film.getAverageRating());
        assertEquals(expectedVotedCount, film.getVotedCount());
    }

}
