package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmUserLikesStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaseFilmService implements FilmService {

    private final InMemoryFilmStorage filmStorage;
    private final InMemoryFilmUserLikesStorage filmUserLikesStorage;

    @Override
    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    @Override
    public Film findFilm(long filmId) {
        return filmStorage.getFilm(filmId).orElse(null);
    }

    @Override
    public Film create(Film film) {
        return filmStorage.create(film);
    }

    @Override
    public Film update(Film film) {
        return filmStorage.update(film);
    }

    @Override
    public void addLike(long filmId, long userId) {
        Film film = getFilmOrThrowError(filmId);
        filmUserLikesStorage.add(film, userId);
    }

    @Override
    public void removeLike(long filmId, long userId) {
        Film film = getFilmOrThrowError(filmId);
        filmUserLikesStorage.remove(film, userId);
    }

    @Override
    public List<Film> getMostPopularFilms(Integer count) {
        return filmStorage
                .getAll()
                .stream()
                .sorted(Comparator.comparingInt(f -> filmUserLikesStorage.getFilmLikes((Film) f).size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    private Film getFilmOrThrowError(long filmId) {
        return filmStorage.getFilm(filmId).orElseThrow(() -> {
            log.info(String.format("Ошибка получения фильма с id: %s. Фильм не найден", filmId));
            return new NotFoundException(String.format("Фильм с id %s не найден", filmId));
        });
    }

}
