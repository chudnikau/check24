package com.check24.domain;

import com.check24.enums.Genres;

public class Film {

    private Long id;
    private String filmName;
    private Double averageRating;
    private Genres genre;
    private String director;
    private Integer votedCount;
    private Boolean isUserVoted;

    public Film(Long id,
                String filName,
                Double averageRating,
                Genres genre,
                String director,
                Integer votedCount,
                Boolean isUserVoted) {
        this.id = id;
        this.filmName = filName;
        this.averageRating = averageRating;
        this.genre = genre;
        this.director = director;
        this.votedCount = votedCount;
        this.isUserVoted = isUserVoted;
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

    public Integer getVotedCount() {
        return votedCount;
    }

    public void setVotedCount(Integer votedCount) {
        this.votedCount = votedCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getUserVoted() {
        return isUserVoted;
    }

    public void setUserVoted(Boolean userVoted) {
        isUserVoted = userVoted;
    }
}
