package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmDirectorsRepository;
import ru.yandex.practicum.filmorate.dao.mapper.DirectorRowMapper;
import ru.yandex.practicum.filmorate.dao.mapper.FilmDirectorRowMapper;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FilmDirectorsRepositoryIml implements FilmDirectorsRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Map<Long, Set<Director>> findDirectorsByFilmIds(List<Long> filmIds) {
        final String sqlQuery = "SELECT FD.FILM_ID, D.* " +
                "FROM DIRECTORS D " +
                "RIGHT JOIN FILM_DIRECTORS FD on D.ID = FD.DIRECTOR_ID AND FD.FILM_ID IN (:filmIds)";

        List<HashMap<Long, Director>> filmDirectors =
                jdbcOperations.query(sqlQuery, Map.of("filmIds", filmIds), new FilmDirectorRowMapper());

        HashMap<Long, Set<Director>> filmDirectorsMap = new HashMap<>();

        for (HashMap<Long, Director> filmDirector : filmDirectors) {
            for (long filmId : filmDirector.keySet()) {
                if (filmDirectorsMap.containsKey(filmId)) {
                    Set<Director> directors = filmDirectorsMap.get(filmId);
                    directors.add(filmDirector.get(filmId));
                    filmDirectorsMap.put(filmId, directors);
                } else {
                    filmDirectorsMap.put(filmId, new HashSet<>(List.of(filmDirector.get(filmId))));
                }
            }
        }

        return filmDirectorsMap;
    }

    @Override
    public Set<Director> findDirectorsByFilmId(long filmId) {
        final String sqlQuery = "SELECT D.* " +
                "FROM DIRECTORS D " +
                "LEFT JOIN FILM_DIRECTORS FD on D.ID = FD.DIRECTOR_ID " +
                "WHERE FD.FILM_ID = :filmId";

        return new HashSet<>(jdbcOperations.query(sqlQuery, Map.of("filmId", filmId), new DirectorRowMapper()));
    }

    @Override
    public void setFilmDirectors(long filmId, Set<Director> directors) {
        StringBuilder sqlQuery = new StringBuilder("INSERT INTO FILM_DIRECTORS (FILM_ID, DIRECTOR_ID) VALUES ");

        for (Director director : directors) {
            sqlQuery.append(String.format("(%s, %s),", filmId, director.getId()));
        }
        sqlQuery.deleteCharAt(sqlQuery.length() - 1);

        jdbcOperations.update(sqlQuery.toString(), Map.of());
    }

    @Override
    public void deleteFilmDirectors(long filmId, Set<Director> directors) {
        String sqlQuery = "DELETE FROM FILM_DIRECTORS WHERE FILM_ID = :film_id AND DIRECTOR_ID IN (:ids)";

        jdbcOperations.update(sqlQuery, Map.of("film_id", filmId,
                "ids", directors.stream().map(Director::getId).collect(Collectors.toList())));
    }
}
