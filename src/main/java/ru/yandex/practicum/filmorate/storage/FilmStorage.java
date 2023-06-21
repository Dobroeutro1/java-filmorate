package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage extends Storage<Film> {

    List<Film> getAll();

    Optional<Film> getFilm(long filmId);

    Film create(Film item);

    Film update(Film item);

}
