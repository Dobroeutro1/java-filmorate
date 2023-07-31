package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmUserLikesRepository {

    List<Film> getFilteredMostPopularFilms(Integer year, Integer genreId, Integer count);

    void add(long filmId, long userId);

    void remove(long filmId, long userId);

    List<Film> getCommonFilms(long userId, long friendId);

    List<Film> getLikedFilmsByUser(long userId);

}
