package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;
import java.util.Optional;

public interface MpaRepository {

    List<MPA> getAll();

    Optional<MPA> findById(long id);

}
