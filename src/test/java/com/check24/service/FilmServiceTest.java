package com.check24.service;

import com.check24.entity.FilmEntity;
import com.check24.enums.Genres;
import com.check24.exceptions.FilmAbsentException;
import com.check24.repository.FilmRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmServiceTest {

    @Autowired
    private FilmService filmService;

    @MockBean
    private FilmRepository filmRepository;

    @Before
    public void setUp() {
        when(filmRepository.findById(anyLong())).thenReturn(
                Optional.of(
                        new FilmEntity(
                                "Mister bean",
                                0d,
                                Genres.Comedy,
                                "John Howard Davies",
                                0
                        )
                )
        );
    }

    @Test
    public void testPuttingRatting() throws FilmAbsentException {

        filmService.putRating(1L, 1);

        verify(filmRepository, atLeastOnce())
                .findById(anyLong());

        verify(filmRepository, atLeastOnce())
                .putFilmRate(anyLong(), anyDouble(), anyInt());
    }

    @Test
    public void testCalcSinglePuttingRatting() throws FilmAbsentException {

        filmService.putRating(1L, 5);

        verify(filmRepository, atLeastOnce())
                .putFilmRate(1L, 5d, 1);
    }

    @Test
    public void testCalcMultiplePuttingRatting() throws FilmAbsentException {

        int votedCount = 5;
        double averageRating = (double) (1 + 2 + 3 + 4 + 5) / votedCount;

        when(filmRepository.findById(anyLong())).thenReturn(
                Optional.of(
                        new FilmEntity(
                                "Mister bean",
                                averageRating,
                                Genres.Comedy,
                                "John Howard Davies",
                                votedCount
                        )
                )
        );

        int newRating = 3;
        int newVotedCount = votedCount + 1;

        filmService.putRating(1L, newRating);

        Double expectedRating = (double) (1 + 2 + 3 + 4 + 5 + newRating) / newVotedCount;

        verify(filmRepository, atLeastOnce())
                .putFilmRate(1L, expectedRating, newVotedCount);
    }

    @Test(expected = FilmAbsentException.class)
    public void testPuttingRatingWhenFilmAbsent() throws FilmAbsentException {
        when(filmRepository.findById(anyLong())).thenReturn(Optional.empty());
        filmService.putRating(1L, 1);
    }

}
