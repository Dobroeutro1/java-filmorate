package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmUserLikesRepository;
import ru.yandex.practicum.filmorate.dao.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Slf4j
@Repository
public class FilmUserLikesRepositoryImpl implements FilmUserLikesRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public FilmUserLikesRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Film> getMostPopularFilms(Integer count) {
        final String sqlQuery = "SELECT F.*, M.NAME AS MPA_NAME " +
                "FROM FILMS F " +
                "LEFT JOIN MPA M on M.ID = F.MPA_ID " +
                "LEFT JOIN FILM_LIKES FL on F.ID = FL.FILM_ID " +
                "GROUP BY F.ID " +
                "ORDER BY COUNT(FL.USER_ID) DESC " +
                "LIMIT :count";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("count", count);

        return jdbcOperations.query(sqlQuery, map, new FilmRowMapper());
    }

    @Override
    public void add(long filmId, long userId) {
        final String sqlQuery = "INSERT INTO FILM_LIKES(film_id, user_id) VALUES (:film_id, :user_id)";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("film_id", filmId);
        map.addValue("user_id", userId);

        jdbcOperations.update(sqlQuery, map);
        log.info(String.format("Добавлен лайк фильму %s от пользователя %s", filmId, userId));
    }

    @Override
    public void remove(long filmId, long userId) {
        final String sqlQuery = "DELETE FROM FILM_LIKES WHERE FILM_ID = :film_id AND USER_ID = :user_id";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("film_id", filmId);
        map.addValue("user_id", userId);

        jdbcOperations.update(sqlQuery, map);
        log.info(String.format("Убран лайк фильму %s от пользователя %s", filmId, userId));
    }

}
