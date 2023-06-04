package com.check24.controller;

import com.check24.exceptions.FilmAbsentException;
import com.check24.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/films")
public class FilmController {

    private FilmService fileService;

    @Autowired
    public FilmController(FilmService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/all")
    public String allFilms(Model model) {
        model.addAttribute("filmsModel",
                fileService.allFilms());
        return "films";
    }

    @GetMapping(value = "/rate/{filmId}/{ratingValue}")
    public String rateFilm(@PathVariable Long filmId,
                           @PathVariable Integer ratingValue) throws FilmAbsentException {
        fileService.putRating(filmId, ratingValue);
        return "redirect:/films/all";
    }

}
