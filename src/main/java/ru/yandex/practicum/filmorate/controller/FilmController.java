package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/films")
public class FilmController {
    FilmStorage storage = new FilmStorage();

    @GetMapping()
    public List<Film> getAll() {
        log.info("GET-запрос к эндпоинту: '/films'");
        return storage.getAll();
    }

    @PostMapping()
    public Film create(@Valid @RequestBody Film film) {
        log.info("POST-запрос к эндпоинту: '/films'");
        return storage.create(film);
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) {
        log.info("PUT-запрос к эндпоинту: '/films'");
        return storage.update(film);
    }
}
