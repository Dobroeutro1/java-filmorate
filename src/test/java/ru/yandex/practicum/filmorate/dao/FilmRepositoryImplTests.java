package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.dao.impl.FilmRepositoryImpl;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Sql(scripts = "/init_films.sql")
@Import(FilmRepositoryImpl.class)
public class FilmRepositoryImplTests {

    @Autowired
    private FilmRepository filmRepository;

    @Test
    public void testGetAll() {
        List<Film> films = filmRepository.getAll();

        assertEquals(4, films.size());
        assertEquals(1, films.get(0).getId());
        assertEquals("Фильм 1", films.get(0).getName());
        assertEquals("Описание 1", films.get(0).getDescription());
        assertEquals(LocalDate.of(2020, 1, 1), films.get(0).getReleaseDate());
        assertEquals(1600, films.get(0).getDuration());
        assertEquals(1, films.get(0).getMpa().getId());
        assertNotNull(films.get(0).getGenres());
        assertEquals(0, films.get(0).getGenres().size());
    }

    @Test
    public void testGetFilm() {
        Optional<Film> filmOpt = filmRepository.getFilm(1);

        assertFalse(filmOpt.isEmpty());

        Film film = filmOpt.get();

        assertEquals(1, film.getId());
        assertEquals("Фильм 1", film.getName());
        assertEquals("Описание 1", film.getDescription());
        assertEquals(LocalDate.of(2020, 1, 1), film.getReleaseDate());
        assertEquals(1600, film.getDuration());
        assertEquals(1, film.getMpa().getId());
        assertNotNull(film.getGenres());
        assertEquals(0, film.getGenres().size());
    }

    @Test
    public void testCreateFilm() {
        Film film = new Film(0, "name", "description",
                LocalDate.of(1967, 3, 25), 100, new MPA(1, "mpa"),
                new HashSet<>());

        Film createdFilm = filmRepository.create(film);

        assertNotNull(createdFilm);
        assertEquals(5, createdFilm.getId());
        assertEquals("name", createdFilm.getName());
        assertEquals("description", createdFilm.getDescription());
        assertEquals(LocalDate.of(1967, 3, 25), createdFilm.getReleaseDate());
        assertEquals(100, createdFilm.getDuration());
        assertEquals(1, createdFilm.getMpa().getId());
        assertNotNull(createdFilm.getGenres());
        assertEquals(0, createdFilm.getGenres().size());
    }

    @Test
    public void testUpdateFilm() {
        Film film = new Film(1, "name", "description",
                LocalDate.of(1967, 3, 25), 100, new MPA(1, "mpa"),
                new HashSet<>());

        Film updatedFilm = filmRepository.update(film);

        assertNotNull(updatedFilm);
        assertEquals(1, updatedFilm.getId());
        assertEquals("name", updatedFilm.getName());
        assertEquals("description", updatedFilm.getDescription());
        assertEquals(LocalDate.of(1967, 3, 25), updatedFilm.getReleaseDate());
        assertEquals(100, updatedFilm.getDuration());
        assertEquals(1, updatedFilm.getMpa().getId());
        assertNotNull(updatedFilm.getGenres());
        assertEquals(0, updatedFilm.getGenres().size());
    }
}
