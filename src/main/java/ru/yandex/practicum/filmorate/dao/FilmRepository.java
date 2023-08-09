package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.enums.SearchFilmBy;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FilmRepository extends Repository<Film> {

    List<Film> getAll();

    Optional<Film> getFilm(long filmId);

    void deleteFilm(long filmId);

    List<Film> getSortedByYearFilmsOfDirector(long directorId);

    List<Film> getSortedByLikesFilmsOfDirector(long directorId);

    List<Film> searchFilmByNameAndDirectors(String query, Set<SearchFilmBy> by);

}
