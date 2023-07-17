package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DirectorRepository extends Repository<Director> {

    Optional<Director> findById(long id);

    void delete(long directorId);

    Set<Director> findByIds(List<Long> ids);
}
