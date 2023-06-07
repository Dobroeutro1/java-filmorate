package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

@Slf4j
public class UserStorage extends Storage<User> {

    public User create(User user) {
        lastId++;
        user.setId(lastId);

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        items.put(lastId, user);
        log.info("Создание пользователя: " + user);
        return user;
    }

    public User update(User user) {
        if (!items.containsKey(user.getId())) {
            log.error("Изменение несуществующего пользователя: " + user);
            throw new NotFoundException("Такого пользователя не существует!");
        }

        items.put(user.getId(), user);
        return user;
    }

}
