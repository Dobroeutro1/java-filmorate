package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.DirectorRepository;
import ru.yandex.practicum.filmorate.dao.mapper.DirectorRowMapper;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DirectorRepositoryImpl implements DirectorRepository {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Optional<Director> findById(long id) {
        final String sqlQuery = "SELECT * FROM DIRECTORS WHERE ID = :directorId";

        List<Director> directorId = jdbcOperations.query(sqlQuery,
                Map.of("directorId", id), new DirectorRowMapper());

        if (directorId.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(directorId.get(0));
    }

    @Override
    public List<Director> getAll() {
        final String sqlQuery = "SELECT * FROM DIRECTORS";

        return jdbcOperations.query(sqlQuery, new DirectorRowMapper());
    }

    @Override
    public Set<Director> findByIds(List<Long> ids) {
        final String sqlQuery = "SELECT * FROM DIRECTORS WHERE ID IN (:ids)";
        return new HashSet<>(jdbcOperations.query(sqlQuery, Map.of("ids", ids), new DirectorRowMapper()));
    }

    @Override
    public Director create(Director director) {
        log.info("Создание режиссёра: " + director);
        String sqlQuery = "INSERT INTO DIRECTORS (name) values (:name)";

        director.setId(sqlUpdate(sqlQuery, director));
        return director;
    }

    @Override
    public Director update(Director director) {
        log.info("Изменение режиссёра: " + director);
        String sqlQuery = "UPDATE DIRECTORS SET name = :name WHERE id = :directorId";

        sqlUpdate(sqlQuery, director);
        return director;
    }

    @Override
    public void delete(long directorId) {
        String sqlQuery = "DELETE FROM DIRECTORS WHERE ID = :directorId";
        jdbcOperations.update(sqlQuery, Map.of("directorId", directorId));
    }

    private long sqlUpdate(String sqlQuery, Director director) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("name", director.getName());
        map.addValue("directorId", director.getId());

        jdbcOperations.update(sqlQuery, map, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

}
