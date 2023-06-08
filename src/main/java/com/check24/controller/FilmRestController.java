package com.check24.controller;

import com.check24.domain.Film;
import com.check24.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/films")
public class FilmRestController {

    private FilmService fileService;

    @Autowired
    public FilmRestController(FilmService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/search/{filmName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Film> searchFilmsByName(@PathVariable String filmName) {
        return fileService.searchByName(filmName);
    }
}
