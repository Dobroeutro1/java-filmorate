package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.MpaRepository;
import ru.yandex.practicum.filmorate.dao.mapper.MpaRowMapper;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class MpaRepositoryImpl implements MpaRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public MpaRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<MPA> getAll() {
        final String sqlQuery = "SELECT * FROM MPA";
        return jdbcOperations.query(sqlQuery, new MpaRowMapper());
    }

    @Override
    public Optional<MPA> findById(long id) {
        final String sqlQuery = "SELECT * FROM MPA WHERE ID = :id";
        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("id", id);

        List<MPA> mpas = jdbcOperations.query(sqlQuery, map, new MpaRowMapper());

        if (mpas.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(mpas.get(0));
    }

}
