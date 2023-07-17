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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

}
