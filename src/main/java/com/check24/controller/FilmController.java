package com.check24.controller;

import com.check24.domain.Film;
import com.check24.exceptions.FilmAbsentException;
import com.check24.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private FilmService fileService;

    @Autowired
    public FilmController(FilmService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Film> allFilms() {
        return fileService.allFilms();
    }

    @GetMapping(value = "/search/{filmName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Film filmsByName(@PathVariable String filmName) {
        return fileService.searchByName(filmName);
    }

    @PutMapping(value = "/raite/{id}/{ratingValue}")
    public String filmsByName(@PathVariable Long id, @PathVariable Integer ratingValue) throws FilmAbsentException {
        fileService.putRating(id, ratingValue);
        return "/";
    }

}
