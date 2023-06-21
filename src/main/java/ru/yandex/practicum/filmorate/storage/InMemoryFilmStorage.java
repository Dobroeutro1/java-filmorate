package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private long lastId = 0;

    private final HashMap<Long, Film> films = new HashMap<>();

    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    public Optional<Film> getFilm(long filmId) {
        return Optional.ofNullable(films.get(filmId));
    }

    public Film create(Film film) {
        lastId++;
        film.setId(lastId);
        log.info("Создание фильма: " + film);
        films.put(lastId, film);
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
