package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.*;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaseFilmService implements FilmService {

    private final FilmRepository filmRepository;
    private final FilmUserLikesRepository filmUserLikesRepository;
    private final FilmGenresRepository filmGenresRepository;
    private final MpaRepository mpaRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<Film> getAll() {
        List<Film> films = filmRepository.getAll();
        return getFilms(films);
    }

    @Override
    public Film findFilm(long filmId) {
        Film film = filmRepository.getFilm(filmId).orElseThrow(() -> {
            log.info(String.format("Ошибка получения фильма с id: %s. Фильм не найден", filmId));
            return new NotFoundException(String.format("Фильм с id %s не найден", filmId));
        });

        Set<Genre> genres = filmGenresRepository.findGenresByFilmId(filmId);

        film.setGenres(genres != null ? genres : new HashSet<>());

        return film;
    }

    @Override
    public Film create(Film film) {
        film.setMpa(findMpa(film.getMpa().getId()));

        Set<Genre> genres = new HashSet<>();

        if (film.getGenres() != null) {
            genres = genreRepository.findByIds(film.getGenres().stream().map(Genre::getId).collect(Collectors.toList()));
        }

        film.setGenres(genres);

        Film createdFilm = filmRepository.create(film);

        if (!genres.isEmpty()) {
            filmGenresRepository.setFilmGenres(film.getId(), genres);
        }

        return createdFilm;
    }

    @Override
    public Film update(Film film) {
        findFilm(film.getId());
        Set<Genre> genres = updateGenres(film);
        film.setGenres(genres != null ? genres : new HashSet<>());
        film.setMpa(findMpa(film.getMpa().getId()));

        return filmRepository.update(film);
    }

    @Override
    public void delete(long filmId) {
        findFilm(filmId);
        filmRepository.deleteFilm(filmId);
    }

    @Override
    public void addLike(long filmId, long userId) {
        filmUserLikesRepository.add(filmId, userId);
    }

    @Override
    public void removeLike(long filmId, long userId) {
        filmUserLikesRepository.remove(filmId, userId);
    }

    @Override
    public List<Film> getMostPopularFilms(Integer count) {
        List<Film> films = filmUserLikesRepository.getMostPopularFilms(count);
        return getFilms(films);
    }

    @Override
    public List<Film> getCommonFilms(long userId, long friendId) {
        List<Film> films = filmUserLikesRepository.getCommonFilms(userId, friendId);
        return getFilms(films);
    }

    private MPA findMpa(long mpaId) {
        return mpaRepository.findById(mpaId).orElseThrow(() -> {
            log.info(String.format("Ошибка получения MPA с id: %s. MPA не найден", mpaId));
            return new NotFoundException(String.format("MPA с id %s не найден", mpaId));
        });
    }

    private Set<Genre> updateGenres(Film film) {
        if (film.getGenres() != null) {
            List<Long> filmGenreIds = film.getGenres().stream().map(Genre::getId).collect(Collectors.toList());
            Set<Genre> filmGenres = genreRepository.findByIds(filmGenreIds);
            Set<Genre> filmGenresFromDb = filmGenresRepository.findGenresByFilmId(film.getId());
            Set<Genre> genresForDelete = filmGenresFromDb.stream()
                    .filter(genre -> !filmGenreIds.contains(genre.getId())).collect(Collectors.toSet());
            Set<Genre> genresForAdd = filmGenres.stream()
                    .filter(genre -> !filmGenresFromDb.contains(genre)).collect(Collectors.toSet());

            if (!genresForDelete.isEmpty()) {
                filmGenresRepository.deleteFilmGenres(film.getId(), genresForDelete);
            }

            if (!genresForAdd.isEmpty()) {
                filmGenresRepository.setFilmGenres(film.getId(), genresForAdd);
            }

            return filmGenres;
        }

        return null;
    }

    private Map<Long, Set<Genre>> findGenresByFilmIds(List<Long> filmIds) {
        if (filmIds.isEmpty()) {
            return new HashMap<>();
        }

        return filmGenresRepository.findGenresByFilmIds(filmIds);
    }

    private List<Film> getFilms(List<Film> films) {
        Map<Long, Set<Genre>> filmGenresMap = findGenresByFilmIds(films.stream().map(Film::getId)
                .collect(Collectors.toList()));

        for (Film film : films) {
            film.setGenres(filmGenresMap.get(film.getId()) != null ? filmGenresMap.get(film.getId()) : new HashSet<>());
        }

        return films;
    }

}
