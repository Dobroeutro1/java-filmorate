package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FilmGenresRepository {

    Map<Long, Set<Genre>> findGenresByFilmIds(List<Long> filmIds);

    Set<Genre> findGenresByFilmId(long filmId);

    void setFilmGenres(long filmId, Set<Genre> genres);

    void deleteFilmGenres(long filmId, Set<Genre> genres);

}
