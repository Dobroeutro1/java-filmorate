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

    public List<Film> getCommonFilms(long userId, long friendId) {
        final String sqlQuery = "SELECT f.ID, f.NAME, f.DESCRIPTION, f.DURATION, f.RELEASE_DATE ,f.MPA_ID, " +
                "m.NAME as MPA_NAME " +
                "FROM FILMS f " +
                "RIGHT JOIN (SELECT f.id, count(fl.FILM_ID) AS likes " +
                "FROM FILMS f " +
                "INNER JOIN FILM_LIKES fl ON f.ID = fl.FILM_ID " +
                "GROUP BY f.ID ) AS rule1 ON f.id = rule1.id " +
                "LEFT JOIN (SELECT f.ID,fl.USER_ID FROM FILMS f " +
                "INNER JOIN FILM_LIKES fl ON f.ID = fl.FILM_ID " +
                "WHERE (fl.USER_ID = :user_id AND fl.USER_ID = :friend_id)) AS rule2 ON f.id = rule2.id " +
                "LEFT JOIN mpa m ON f.MPA_ID = m.ID " +
                "GROUP BY f.ID " +
                "ORDER BY rule1.likes DESC ";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("user_id", userId);
        map.addValue("friend_id", friendId);

        return jdbcOperations.query(sqlQuery, map, new FilmRowMapper());
    }

    public List<Film> getCommonFilmsTest(long userId, long friendId) {
        final String sqlQuery = "SELECT f.ID, f.NAME, f.DESCRIPTION, f.DURATION, f.RELEASE_DATE ,f.MPA_ID," +
                "m.NAME AS MPA_NAME " +
                "FROM FILMS f " +
                "RIGHT JOIN (SELECT f.ID,count(fl.user_id) AS likes FROM FILMS f " +
                "INNER JOIN FILM_LIKES fl ON f.ID = fl.FILM_ID " +
                "WHERE f.ID = (" +
                "SELECT f1.ID FROM films f1 " +
                "INNER JOIN FILM_LIKES fl1 ON f1.ID = fl1.FILM_ID " +
                "WHERE fl1.USER_ID = :user_id) AND f.ID = " +
                "( " +
                "SELECT f2.id FROM films f2 " +
                "INNER JOIN FILM_LIKES fl2 ON f2.id = fl2.FILM_ID " +
                "WHERE fl2.USER_ID = :friend_id) " +
                "GROUP BY f.id ) AS rule1 ON f.id = rule1.id " +
                "LEFT JOIN mpa m ON f.MPA_ID = m.ID " +
                "GROUP BY f.ID " +
                "ORDER BY rule1.likes DESC ";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("user_id", userId);
        map.addValue("friend_id", friendId);

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
