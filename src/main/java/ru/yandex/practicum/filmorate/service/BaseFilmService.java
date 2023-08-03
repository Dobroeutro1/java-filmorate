package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.*;
import ru.yandex.practicum.filmorate.enums.EventType;
import ru.yandex.practicum.filmorate.enums.OperationType;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Director;
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
    private final DirectorRepository directorRepository;
    private final FilmDirectorsRepository filmDirectorsRepository;
    private final FeedService feedService;

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
        Set<Director> directors = filmDirectorsRepository.findDirectorsByFilmId(filmId);

        film.setGenres(genres != null ? genres : new HashSet<>());
        film.setDirectors(directors != null ? directors : new HashSet<>());

        return film;
    }

    @Override
    public Film create(Film film) {
        film.setMpa(findMpa(film.getMpa().getId()));

        Set<Genre> genres = new HashSet<>();
        Set<Director> directors = new HashSet<>();

        if (film.getGenres() != null) {
            genres = genreRepository.findByIds(film.getGenres().stream().map(Genre::getId).collect(Collectors.toList()));
        }
        if (film.getDirectors() != null) {
            directors = directorRepository.findByIds(film.getDirectors().stream()
                    .map(Director::getId)
                    .collect(Collectors.toList()));
        }

        film.setGenres(genres);
        film.setDirectors(directors);

        Film createdFilm = filmRepository.create(film);

        if (!genres.isEmpty()) {
            filmGenresRepository.setFilmGenres(film.getId(), genres);
        }
        if (!directors.isEmpty()) {
            filmDirectorsRepository.setFilmDirectors(film.getId(), directors);
        }

        return createdFilm;
    }

    @Override
    public Film update(Film film) {
        findFilm(film.getId());
        Set<Genre> genres = updateGenres(film);
        Set<Director> directors = updateDirectors(film);
        film.setGenres(genres != null ? genres : new HashSet<>());
        film.setDirectors(directors != null ? directors : new HashSet<>());
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
        feedService.saveFeed(userId, filmId, EventType.LIKE, OperationType.ADD);
    }

    @Override
    public void removeLike(long filmId, long userId) {
        feedService.saveFeed(userId, filmId, EventType.LIKE, OperationType.REMOVE);
        filmUserLikesRepository.remove(filmId, userId);
    }

    @Override
    public List<Film> getMostPopularFilms(Integer count) {
        List<Film> films = filmUserLikesRepository.getMostPopularFilms(count);
        return getFilms(films);
    }

    @Override
    public List<Film> getSortedFilmsByDirector(long directorId, String sortField) {
        directorRepository.findById(directorId).orElseThrow(() -> {
            log.info(String.format("Ошибка получения режиссёра с id: %s. Режиссёр не найден", directorId));
            return new NotFoundException(String.format("Режиссёр с id %s не найден", directorId));
        });

        List<Film> films;
        switch (sortField) {
            case "year":
                films = filmRepository.getSortedByYearFilmsOfDirector(directorId);
                break;
            case "likes":
                films = filmRepository.getSortedByLikesFilmsOfDirector(directorId);
                break;
            default:
                return null;
        }

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

    private Set<Director> updateDirectors(Film film) {
        Set<Director> filmDirectorsFromDb = filmDirectorsRepository.findDirectorsByFilmId(film.getId());

        if (film.getDirectors() != null) {
            List<Long> filmDirectorsIds = film.getDirectors().stream()
                    .map(Director::getId)
                    .collect(Collectors.toList());

            Set<Director> filmDirectors = directorRepository.findByIds(filmDirectorsIds);

            Set<Director> directorsForDelete = filmDirectorsFromDb.stream()
                    .filter(director -> !filmDirectorsIds.contains(director.getId()))
                    .collect(Collectors.toSet());
            Set<Director> directorsForAdd = filmDirectors.stream()
                    .filter(director -> !filmDirectorsFromDb.contains(director))
                    .collect(Collectors.toSet());

            if (!directorsForDelete.isEmpty()) {
                filmDirectorsRepository.deleteFilmDirectors(film.getId(), directorsForDelete);
            }
            if (!directorsForAdd.isEmpty()) {
                filmDirectorsRepository.setFilmDirectors(film.getId(), directorsForAdd);
            }

            return filmDirectors;
        } else if (!filmDirectorsFromDb.isEmpty()) {
            filmDirectorsRepository.deleteFilmDirectors(film.getId(), filmDirectorsFromDb);
        }

        return null;
    }

    private List<Film> getFilms(List<Film> films) {
        Map<Long, Set<Genre>> filmGenresMap = findGenresByFilmIds(films.stream().map(Film::getId)
                .collect(Collectors.toList()));
        Map<Long, Set<Director>> filmDirectorsMap = findDirectorsByFilmIds(films.stream().map(Film::getId)
                .collect(Collectors.toList()));

        for (Film film : films) {
            film.setGenres(filmGenresMap.get(film.getId()) != null ? filmGenresMap.get(film.getId()) : new HashSet<>());
            film.setDirectors(
                    filmDirectorsMap.get(film.getId()) != null ? filmDirectorsMap.get(film.getId()) : new HashSet<>());
        }

        return films;
    }

    private Map<Long, Set<Genre>> findGenresByFilmIds(List<Long> filmIds) {
        if (filmIds.isEmpty()) {
            return new HashMap<>();
        }

        return filmGenresRepository.findGenresByFilmIds(filmIds);
    }

    private Map<Long, Set<Director>> findDirectorsByFilmIds(List<Long> filmIds) {
        if (filmIds.isEmpty()) {
            return new HashMap<>();
        }

        return filmDirectorsRepository.findDirectorsByFilmIds(filmIds);
    }
}
