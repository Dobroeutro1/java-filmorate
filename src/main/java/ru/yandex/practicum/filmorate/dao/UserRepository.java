package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User> {

    List<User> getAll();

    Optional<User> getUser(long userId);

}
