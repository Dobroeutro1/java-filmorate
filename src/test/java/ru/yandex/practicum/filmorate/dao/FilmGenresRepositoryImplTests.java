package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.dao.impl.FilmGenresRepositoryImpl;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Sql(scripts = "/init_films.sql")
@Import(FilmGenresRepositoryImpl.class)
public class FilmGenresRepositoryImplTests {

    @Autowired
    private FilmGenresRepository filmGenresRepository;

    @Test
    public void testSetFilmGenres() {
        Set<Genre> genres = new HashSet<>();

        genres.add(new Genre(1, ""));
        genres.add(new Genre(2, ""));
        genres.add(new Genre(3, ""));

        filmGenresRepository.setFilmGenres(1, genres);

        Set<Genre> filmGenres = filmGenresRepository.findGenresByFilmId(1);

        assertEquals(3, filmGenres.size());
    }

    @Test
    public void testDeleteFilmGenres() {
        Set<Genre> genresForAdd = new HashSet<>();
        genresForAdd.add(new Genre(1, ""));
        genresForAdd.add(new Genre(2, ""));
        genresForAdd.add(new Genre(3, ""));

        filmGenresRepository.setFilmGenres(1, genresForAdd);

        Set<Genre> genresForDelete = new HashSet<>();
        genresForDelete.add(new Genre(3, ""));

        filmGenresRepository.deleteFilmGenres(1, genresForDelete);

        Set<Genre> filmGenresAfterDelete = filmGenresRepository.findGenresByFilmId(1);

        assertEquals(2, filmGenresAfterDelete.size());
    }

    @Test
    public void testFindGenresByFilmIds() {
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre(1, ""));
        genres.add(new Genre(2, ""));
        genres.add(new Genre(3, ""));

        filmGenresRepository.setFilmGenres(1, genres);
        filmGenresRepository.setFilmGenres(2, genres);

        Map<Long, Set<Genre>> genresByFilmIds = filmGenresRepository.findGenresByFilmIds(List.of(1L, 2L));

        assertEquals(2, genresByFilmIds.size());
    }

}
