package com.check24.controller;

import com.check24.domain.User;
import com.check24.exceptions.FilmAbsentException;
import com.check24.exceptions.UserAdsentException;
import com.check24.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/films")
public class FilmController {

    private final FilmService fileService;
    private final User currUser;

    @Autowired
    public FilmController(FilmService fileService, User currUser) {
        this.fileService = fileService;
        this.currUser = currUser;
    }

    @GetMapping(value = "/all")
    public String allFilms(Model model) {
        model.addAttribute("films",
                fileService.allVoteAvailableFilms());
        return "films";
    }

    @GetMapping(value = "/rate/{filmId}/{ratingValue}")
    public String rateFilm(@PathVariable Long filmId,
                           @PathVariable Integer ratingValue) throws FilmAbsentException, UserAdsentException {
        fileService.putRating(currUser.getUserId(), filmId, ratingValue);
        return "redirect:/films/all";
    }

}
