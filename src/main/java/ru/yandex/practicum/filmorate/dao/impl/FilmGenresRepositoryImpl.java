package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmGenresRepository;
import ru.yandex.practicum.filmorate.dao.mapper.FilmGenreRowMapper;
import ru.yandex.practicum.filmorate.dao.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class FilmGenresRepositoryImpl implements FilmGenresRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    public FilmGenresRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Map<Long, Set<Genre>> findGenresByFilmIds(List<Long> filmIds) {
        final String sqlQuery = "SELECT FG.FILM_ID, G.* " +
                "FROM GENRES G " +
                "RIGHT JOIN FILM_GENRES FG on G.ID = FG.GENRE_ID AND FG.FILM_ID IN (:filmIds)";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("filmIds", filmIds);

        List<HashMap<Long, Genre>> filmGenres = jdbcOperations.query(sqlQuery, map, new FilmGenreRowMapper());
        HashMap<Long, Set<Genre>> filmGenresMap = new HashMap<>();

        for (HashMap<Long, Genre> filmGenre : filmGenres) {
            for (long filmId : filmGenre.keySet()) {
                if (filmGenresMap.containsKey(filmId)) {
                    Set<Genre> genres = filmGenresMap.get(filmId);
                    genres.add(filmGenre.get(filmId));
                    filmGenresMap.put(filmId, genres);
                } else {
                    filmGenresMap.put(filmId, new HashSet<>(List.of(filmGenre.get(filmId))));
                }
            }
        }

        return filmGenresMap;
    }

    @Override
    public Set<Genre> findGenresByFilmId(long filmId) {
        final String sqlQuery = "SELECT G.* " +
                "FROM GENRES G " +
                "LEFT JOIN FILM_GENRES FG on G.ID = FG.GENRE_ID " +
                "WHERE FG.FILM_ID = :filmId";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("filmId", filmId);

        return new HashSet<>(jdbcOperations.query(sqlQuery, map, new GenreRowMapper()));
    }

    @Override
    public void setFilmGenres(long filmId, Set<Genre> genres) {
        StringBuilder sqlQuery = new StringBuilder("INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID) VALUES ");

        for (Genre genre : genres) {
            sqlQuery.append(String.format("(%s, %s),", filmId, genre.getId()));
        }

        sqlQuery.deleteCharAt(sqlQuery.length() - 1);

        MapSqlParameterSource map = new MapSqlParameterSource();

        jdbcOperations.update(sqlQuery.toString(), map);
    }

    @Override
    public void deleteFilmGenres(long filmId, Set<Genre> genres) {
        String sqlQuery = "DELETE FROM FILM_GENRES WHERE FILM_ID = :film_id AND GENRE_ID IN (:ids)";

        MapSqlParameterSource map = new MapSqlParameterSource();

        map.addValue("film_id", filmId);
        map.addValue("ids", genres.stream().map(Genre::getId).collect(Collectors.toList()));

        jdbcOperations.update(sqlQuery, map);
    }

}
