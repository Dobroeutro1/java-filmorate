package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private long lastId = 0;

    private final HashMap<Long, User> usersMap = new HashMap<>();

    public List<User> getAll() {
        return new ArrayList<>(usersMap.values());
    }

    public Optional<User> getUser(long userId) {
        return Optional.ofNullable(usersMap.get(userId));
    }

    public User create(User user) {
        lastId++;
        user.setId(lastId);

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        log.info("Создание пользователя: " + user);

        usersMap.put(lastId, user);
        return user;
    }

    public User update(User user) {
        log.info("Изменение пользователя: " + user);
        usersMap.put(user.getId(), user);
        return user;
    }

}
