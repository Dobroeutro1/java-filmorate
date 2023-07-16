package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dao.impl.GenreRepositoryImpl;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(GenreRepositoryImpl.class)
public class GenreRepositoryImplTests {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void testGetAll() {
        List<Genre> genres = genreRepository.getAll();

        assertEquals(6, genres.size());
        assertEquals(1, genres.get(0).getId());
        assertEquals("Комедия", genres.get(0).getName());
    }

    @Test
    public void testGetGenre() {
        Optional<Genre> genreOpt = genreRepository.findById(1);

        assertFalse(genreOpt.isEmpty());

        Genre genre = genreOpt.get();

        assertEquals(1, genre.getId());
        assertEquals("Комедия", genre.getName());
    }

    @Test
    public void testGetGenresByIds() {
        List<Long> genreIds = Arrays.asList(1L, 2L);
        Set<Genre> genres = genreRepository.findByIds(genreIds);

        assertEquals(2, genres.size());
        assertEquals(1, genres.stream().findFirst().get().getId());
        assertEquals("Комедия", genres.stream().findFirst().get().getName());
    }

}
