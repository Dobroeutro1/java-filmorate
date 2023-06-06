package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class UserStorage {
    private long lastId = 0;
    private final HashMap<Long, User> users = new HashMap<>();

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public User create(User user) {
        lastId++;
        user.setId(lastId);

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        users.put(lastId, user);
        log.info("Создание пользователя: " + user);
        return user;
    }

    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            log.error("Изменение несуществующего пользователя: " + user);
            throw new NotFoundException("Такого пользователя не существует!");
        }

        users.put(user.getId(), user);
        return user;
    }
}
