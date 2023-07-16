package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dao.impl.FilmUserLikesRepositoryImpl;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(FilmUserLikesRepositoryImpl.class)
public class FilmUserLikesRepositoryImplTests {

    @Autowired
    private FilmUserLikesRepository filmUserLikesRepository;

    @Test
    public void testGetMostPopularFilms() {
        List<Film> films = filmUserLikesRepository.getMostPopularFilms(10);

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
    public void testAddLike() {
        filmUserLikesRepository.add(2, 1);

        List<Film> films = filmUserLikesRepository.getMostPopularFilms(10);

        assertEquals(4, films.size());
        assertEquals(2, films.get(0).getId());
        assertEquals("Фильм 2", films.get(0).getName());
        assertEquals("Описание 2", films.get(0).getDescription());
        assertEquals(LocalDate.of(2020, 2, 2), films.get(0).getReleaseDate());
        assertEquals(2600, films.get(0).getDuration());
        assertEquals(2, films.get(0).getMpa().getId());
        assertNotNull(films.get(0).getGenres());
        assertEquals(0, films.get(0).getGenres().size());
    }

    @Test
    public void testRemoveLike() {
        filmUserLikesRepository.add(2, 1);

        List<Film> films = filmUserLikesRepository.getMostPopularFilms(10);

        assertEquals(4, films.size());
        assertEquals(2, films.get(0).getId());
        assertEquals("Фильм 2", films.get(0).getName());
        assertEquals("Описание 2", films.get(0).getDescription());
        assertEquals(LocalDate.of(2020, 2, 2), films.get(0).getReleaseDate());
        assertEquals(2600, films.get(0).getDuration());
        assertEquals(2, films.get(0).getMpa().getId());
        assertNotNull(films.get(0).getGenres());
        assertEquals(0, films.get(0).getGenres().size());

        filmUserLikesRepository.remove(2, 1);

        List<Film> filmsAfterDeleteLike = filmUserLikesRepository.getMostPopularFilms(10);

        assertEquals(4, filmsAfterDeleteLike.size());
        assertEquals(1, filmsAfterDeleteLike.get(0).getId());
        assertEquals("Фильм 1", filmsAfterDeleteLike.get(0).getName());
        assertEquals("Описание 1", filmsAfterDeleteLike.get(0).getDescription());
        assertEquals(LocalDate.of(2020, 1, 1), filmsAfterDeleteLike.get(0).getReleaseDate());
        assertEquals(1600, filmsAfterDeleteLike.get(0).getDuration());
        assertEquals(1, filmsAfterDeleteLike.get(0).getMpa().getId());
        assertNotNull(filmsAfterDeleteLike.get(0).getGenres());
        assertEquals(0, filmsAfterDeleteLike.get(0).getGenres().size());
    }

}
