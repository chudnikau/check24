package com.check24.domain;

import com.check24.enums.Genres;

public class Film {

    private String filmName;
    private Double averageRating;
    private Genres genre;
    private String director;

    private Integer voitedCount;

    public Film(String filName, Double averageRating, Genres genre, String director, Integer voitedCount) {
        this.filmName = filName;
        this.averageRating = averageRating;
        this.genre = genre;
        this.director = director;
        this.voitedCount = voitedCount;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getVoitedCount() {
        return voitedCount;
    }

    public void setVoitedCount(Integer voitedCount) {
        this.voitedCount = voitedCount;
    }
}
