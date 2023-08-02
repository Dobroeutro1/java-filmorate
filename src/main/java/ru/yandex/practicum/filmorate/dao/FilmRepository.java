package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository extends Repository<Film> {

    List<Film> getAll();

    Optional<Film> getFilm(long filmId);

    void deleteFilm(long filmId);

}
