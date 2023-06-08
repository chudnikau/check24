package com.check24.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {

    public UserEntity() {
    }

    public UserEntity(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    private String userName;

    @ManyToMany(mappedBy = "userEntity")
    private Set<FilmEntity> filmEntities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<FilmEntity> getFilmEntities() {
        return filmEntities;
    }

    public void setFilmEntities(Set<FilmEntity> filmEntities) {
        this.filmEntities = filmEntities;
    }
}
