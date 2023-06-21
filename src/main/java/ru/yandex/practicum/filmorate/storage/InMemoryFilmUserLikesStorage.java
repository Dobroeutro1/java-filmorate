package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class InMemoryFilmUserLikesStorage implements FilmUserLikesStorage {

    private final HashMap<Long, Set<Long>> filmUserLikesMap = new HashMap<>();

    public Set<Long> getFilmLikes(Film film) {
        return filmUserLikesMap.computeIfAbsent(film.getId(), id -> new HashSet<>());
    }

    @Override
    public void add(Film film, long userId) {
        Set<Long> filmUserIds = filmUserLikesMap.computeIfAbsent(film.getId(), id -> new HashSet<>());
        filmUserIds.add(userId);
        log.info(String.format("Добавлен лайк фильму %s от пользователя %s", film.getId(), userId));
    }

    @Override
    public void remove(Film film, long userId) {
        Set<Long> filmUserIds = filmUserLikesMap.computeIfAbsent(film.getId(), id -> new HashSet<>());
        filmUserIds.remove(userId);
        log.info(String.format("Удален лайк фильма %s от пользователя %s", film.getId(), userId));
    }

}
