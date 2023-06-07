package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

@Slf4j
public class FilmStorage extends Storage<Film> {

    public Film create(Film film) {
        lastId++;
        film.setId(lastId);
        items.put(lastId, film);
        log.info("Создание фильма: " + film);
        return film;
    }

    public Film update(Film film) {
        if (!items.containsKey(film.getId())) {
            log.error("Изменение несуществующего фильма: " + film);
            throw new NotFoundException("Такого фильма не существует!");
        }

        items.put(film.getId(), film);
        return film;
    }

}
