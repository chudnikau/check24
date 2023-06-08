package com.check24.entity;

import com.check24.enums.Genres;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "films")
public class FilmEntity {

    public FilmEntity() {

    }

    public FilmEntity(Set<UserEntity> userEntity,
                      String filmName,
                      Double averageRating,
                      Genres genre,
                      String director,
                      Integer votedCount) {
        this.userEntity =
                Optional.ofNullable(userEntity).orElse(new HashSet<>());
        this.filmName = filmName;
        this.averageRating = averageRating;
        this.genre = genre;
        this.director = director;
        this.votedCount = votedCount;
    }

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "film_rating",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"))
    private Set<UserEntity> userEntity = new HashSet<>();

    private String filmName;

    private Double averageRating;

    @Enumerated(EnumType.STRING)
    private Genres genre;

    private String director;

    private Integer votedCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<UserEntity> getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(Set<UserEntity> userEntity) {
        this.userEntity = userEntity;
    }
}