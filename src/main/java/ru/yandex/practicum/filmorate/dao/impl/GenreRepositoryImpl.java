package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.GenreRepository;
import ru.yandex.practicum.filmorate.dao.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Repository
public class GenreRepositoryImpl implements GenreRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public GenreRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Genre> getAll() {
        final String sqlQuery = "SELECT * FROM GENRES";
        return jdbcOperations.query(sqlQuery, new GenreRowMapper());
    }

    @Override
    public Optional<Genre> findById(long id) {
        final String sqlQuery = "SELECT * FROM GENRES WHERE ID = :id";
        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("id", id);

        List<Genre> genres = jdbcOperations.query(sqlQuery, map, new GenreRowMapper());

        if (genres.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(genres.get(0));
    }

    @Override
    public Set<Genre> findByIds(List<Long> ids) {
        final String sqlQuery = "SELECT * FROM GENRES WHERE ID IN (:ids)";
        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("ids", ids);

        return new HashSet<>(jdbcOperations.query(sqlQuery, map, new GenreRowMapper()));
    }


}
