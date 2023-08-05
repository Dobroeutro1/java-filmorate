package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmRepository;
import ru.yandex.practicum.filmorate.dao.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.SearchFilmBy;

import java.util.*;

@Slf4j
@Repository
public class FilmRepositoryImpl implements FilmRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public FilmRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Film> getAll() {
        final String sqlQuery = "SELECT F.*, M.NAME AS MPA_NAME FROM FILMS F " +
                "LEFT JOIN MPA M on F.MPA_ID = M.ID";
        return jdbcOperations.query(sqlQuery, new FilmRowMapper());
    }

    @Override
    public Optional<Film> getFilm(long filmId) {
        final String sqlQuery = "SELECT F.*, M.NAME AS MPA_NAME FROM FILMS F " +
                "LEFT JOIN MPA M on F.MPA_ID = M.ID WHERE F.ID = :filmId";
        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("filmId", filmId);

        List<Film> films = jdbcOperations.query(sqlQuery, map, new FilmRowMapper());

        if (films.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(films.get(0));
    }

    @Override
    public Film create(Film film) {
        log.info("Создание фильма: " + film);

        final String sqlQuery = "INSERT INTO FILMS (NAME, DESCRIPTION, DURATION, RELEASE_DATE, MPA_ID)"
                + "VALUES (:name, :description, :duration, :release_date, :mpa_id)";

        film.setId(sqlUpdate(sqlQuery, film));

        return film;
    }

    @Override
    public Film update(Film film) {
        log.info("Изменение фильма: " + film);

        final String sqlQuery = "UPDATE FILMS "
                + "SET NAME = :name, DESCRIPTION = :description, DURATION = :duration, "
                + "RELEASE_DATE = :release_date, MPA_ID = :mpa_id "
                + "WHERE ID = :filmId";

        sqlUpdate(sqlQuery, film);

        return film;
    }

    @Override
    public void deleteFilm(long filmId) {
        log.info("Удаление фильма с id: " + filmId);
        final String sqlQuery = "DELETE FROM FILMS " +
                "WHERE ID = :filmId";
        jdbcOperations.update(sqlQuery, Map.of("filmId", filmId));
    }

    private long sqlUpdate(String sqlQuery, Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("name", film.getName());
        map.addValue("description", film.getDescription());
        map.addValue("duration", film.getDuration());
        map.addValue("release_date", film.getReleaseDate());
        map.addValue("mpa_id", film.getMpa().getId());
        map.addValue("filmId", film.getId());

        jdbcOperations.update(sqlQuery, map, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<Film> getSortedByYearFilmsOfDirector(long directorId) {
        final String sqlQuery = "SELECT F.*, M.NAME AS MPA_NAME " +
                "FROM FILMS F " +
                "LEFT JOIN MPA M on F.MPA_ID = M.ID " +
                "LEFT JOIN FILM_DIRECTORS FD ON F.ID = FD.FILM_ID " +
                "WHERE FD.DIRECTOR_ID = :directorId " +
                "ORDER BY EXTRACT(YEAR FROM F.RELEASE_DATE)";

        return jdbcOperations.query(sqlQuery, Map.of("directorId", directorId), new FilmRowMapper());
    }

    @Override
    public List<Film> getSortedByLikesFilmsOfDirector(long directorId) {
        final String sqlQuery = "SELECT F.*, M.NAME AS MPA_NAME " +
                "FROM FILMS F " +
                "LEFT JOIN MPA M ON F.MPA_ID = M.ID " +
                "RIGHT JOIN FILM_DIRECTORS FD ON F.ID = FD.FILM_ID AND FD.DIRECTOR_ID = :directorId " +
                "LEFT JOIN FILM_LIKES FL ON F.ID = FL.FILM_ID " +
                "GROUP BY F.ID " +
                "ORDER BY COUNT(FL.USER_ID) DESC";
        return jdbcOperations.query(sqlQuery, Map.of("directorId", directorId), new FilmRowMapper());
    }

    @Override
    public List<Film> searchFilmByNameAndDirectors(String query, Set<SearchFilmBy> bySet) {
        StringBuilder sqlQuery = new StringBuilder(
                "SELECT F.*, M.NAME AS MPA_NAME " +
                        "FROM FILMS F " +
                        "LEFT JOIN MPA M on M.ID = F.MPA_ID " +
                        "LEFT JOIN PUBLIC.FILM_LIKES FL on F.ID = FL.FILM_ID ");
        String sqlDirectors = "LEFT JOIN FILM_DIRECTORS FD ON FD.FILM_ID = F.ID " +
                "LEFT JOIN DIRECTORS D ON D.ID = FD.DIRECTOR_ID ";

        MapSqlParameterSource map = new MapSqlParameterSource();

        if (bySet.contains(SearchFilmBy.director) && bySet.contains(SearchFilmBy.title)) {
            sqlQuery
                    .append(sqlDirectors)
                    .append("WHERE LOWER(F.NAME) LIKE LOWER(:query) OR LOWER(D.NAME) LIKE LOWER(:query)");
        } else if (bySet.contains(SearchFilmBy.director)) {
            sqlQuery.append(sqlDirectors).append("WHERE LOWER(D.NAME) LIKE LOWER(:query)");
        } else if (bySet.contains(SearchFilmBy.title)) {
            sqlQuery.append("WHERE LOWER(F.NAME) LIKE LOWER(:query)");
        }

        sqlQuery.append("GROUP BY F.ID ").append("ORDER BY COUNT(FL.USER_ID) DESC");

        map.addValue("query", "%" + query + "%");

        return jdbcOperations.query(sqlQuery.toString(), map, new FilmRowMapper());
    }

}
