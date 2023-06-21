package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage extends Storage<User> {

    List<User> getAll();

    Optional<User> getUser(long userId);

    User create(User item);

    User update(User item);

}
