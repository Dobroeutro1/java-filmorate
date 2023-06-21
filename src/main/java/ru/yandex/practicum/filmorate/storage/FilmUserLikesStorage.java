package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Set;

public interface FilmUserLikesStorage {

    Set<Long> getFilmLikes(Film film);

    void add(Film film, long userId);

    void remove(Film film, long userId);

}
