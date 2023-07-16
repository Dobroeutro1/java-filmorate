package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.BaseGenreService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {

    final BaseGenreService service;

    @GetMapping()
    public List<Genre> getAll() {
        log.info("GET-запрос к эндпоинту: '/genres'");
        return service.getAll();
    }

    @GetMapping("/{genreId}")
    public Genre findById(@PathVariable("genreId") long genreId) {
        log.info("GET-запрос к эндпоинту: '/genres/{genreId}'");
        return service.findById(genreId);
    }

}
