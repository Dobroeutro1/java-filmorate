package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

public interface DirectorService {

    List<Director> getAll();

    Director findById(long directorId);

    Director create(Director director);

    Director update(Director director);

    Director delete(Long directorId);
}
