package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.BaseFilmService;
import ru.yandex.practicum.filmorate.service.BaseUserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    final BaseFilmService filmService;
    final BaseUserService userService;

    @GetMapping()
    public List<Film> getAll() {
        log.info("GET-запрос к эндпоинту: '/films'");
        return filmService.getAll();
    }

    @GetMapping("/{filmId}")
    public Film findFilm(@PathVariable("filmId") long filmId) {
        log.info("GET-запрос к эндпоинту: '/films/{filmId}'");
        return filmService.findFilm(filmId);
    }

    @PostMapping()
    public Film create(@Valid @RequestBody Film film) {
        log.info("POST-запрос к эндпоинту: '/films'");
        return filmService.create(film);
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) {
        log.info("PUT-запрос к эндпоинту: '/films'");
        return filmService.update(film);
    }

    @DeleteMapping("/{filmId}")
    public void delete(@PathVariable("filmId") long filmId) {
        log.info("DELETE-запрос к эндпоинту: '/films/{filmId}");
        filmService.delete(filmId);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable("filmId") long filmId, @PathVariable("userId") long userId) {
        log.info("PUT-запрос к эндпоинту: '/films/{filmId}/like/{userId}'");
        filmService.addLike(filmId, userService.findUser(userId).getId());
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void removeLike(@PathVariable("filmId") long filmId, @PathVariable("userId") long userId) {
        log.info("DELETE-запрос к эндпоинту: '/films/{filmId}/like/{userId}'");
        filmService.removeLike(filmId, userService.findUser(userId).getId());
    }

    @GetMapping("/popular")
    public List<Film> getMostPopularFilms(@RequestParam(defaultValue = "10", required = false) Integer count,
                                          @RequestParam(required = false) Integer year,
                                          @RequestParam(required = false) Integer genreId) {
        log.info("GET-запрос к эндпоинту: '/films/popular'");
        return filmService.getMostPopularFilms(count, year, genreId);
    }

    @GetMapping("/common")
    public List<Film> getCommonFilms(@RequestParam long userId, @RequestParam long friendId) {
        log.info("GET-запрос к эндпоинту: '/films/common?userId={userId}&friendId={friendId}'");
        return filmService.getCommonFilms(userId, friendId);
    }

    @GetMapping("/director/{directorId}")
    public List<Film> getSortedFilms(@PathVariable("directorId") long directorId, @RequestParam String sortBy) {
        log.info("GET-запрос к эндпоинту: 'films/director/{directorId}'");
        return filmService.getSortedFilmsByDirector(directorId, sortBy);
    }

    @GetMapping("/search")
    public List<Film> search(@RequestParam String query, @RequestParam(defaultValue = "director,title", required = false) String by) {
        log.info("GET-запрос к эндпоинту: 'films/search'");
        return filmService.search(query, by);
    }

}
