package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenreRepository {

    List<Genre> getAll();

    Optional<Genre> findById(long id);

    Set<Genre> findByIds(List<Long> ids);

}
