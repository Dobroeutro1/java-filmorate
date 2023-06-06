package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class FilmStorage {
    private long lastId = 0;
    private final HashMap<Long, Film> films = new HashMap<>();

    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    public Film create(Film film) {
        lastId++;
        film.setId(lastId);
        films.put(lastId, film);
        log.info("Создание фильма: " + film);
        return film;
    }

    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            log.error("Изменение несуществующего фильма: " + film);
            throw new NotFoundException("Такого фильма не существует!");
        }

        films.put(film.getId(), film);
        return film;
    }
}
