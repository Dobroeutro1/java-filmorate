package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FilmDirectorsRepository {
    Map<Long, Set<Director>> findDirectorsByFilmIds(List<Long> filmIds);

    Set<Director> findDirectorsByFilmId(long filmId);

    void setFilmDirectors(long filmId, Set<Director> directors);

    void deleteFilmDirectors(long filmId, Set<Director> directors);
}
