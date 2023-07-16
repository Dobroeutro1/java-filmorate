package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dao.impl.MpaRepositoryImpl;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(MpaRepositoryImpl.class)
public class MpaRepositoryImplTests {

    @Autowired
    private MpaRepository mpaRepository;

    @Test
    public void testGetAll() {
        List<MPA> mpas = mpaRepository.getAll();

        assertEquals(5, mpas.size());
        assertEquals(1, mpas.get(0).getId());
        assertEquals("G", mpas.get(0).getName());
    }

    @Test
    public void testGetMpa() {
        Optional<MPA> mpaOpt = mpaRepository.findById(1);

        assertFalse(mpaOpt.isEmpty());

        MPA mpa = mpaOpt.get();

        assertEquals(1, mpa.getId());
        assertEquals("G", mpa.getName());
    }

}
