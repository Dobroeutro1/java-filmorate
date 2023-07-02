package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserFriendsStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaseUserService implements UserService {

    private final InMemoryUserStorage userStorage;
    private final InMemoryUserFriendsStorage userFriendsStorage;

    @Override
    public List<User> getAll() {
        return userStorage.getAll();
    }

    @Override
    public User findUser(long userId) {
        return userStorage.getUser(userId).orElseThrow(() -> {
            log.info(String.format("Ошибка получения пользователя с id: %s. Пользователь не найден", userId));
            return new NotFoundException(String.format("Пользователь с id %s не найден", userId));
        });
    }

    @Override
    public User create(User user) {
        return userStorage.create(user);
    }

    @Override
    public User update(User user) {
        findUser(user.getId());

        return userStorage.update(user);
    }

    @Override
    public List<User> getUserFriends(long userId) {
        User user = userStorage.getUser(userId).orElseThrow(() -> {
            log.info(String.format("Ошибка получения пользователя с id: %s. Пользователь не найден", userId));
            return new NotFoundException(String.format("Пользователь с id %s не найден", userId));
        });

        return userFriendsStorage
                .getUserFriends(user)
                .stream()
                .map((id) -> userStorage.getUser(id).orElse(null))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> addFriend(long userId, long friendId) {
        User user = findUser(userId);
        User friend = findUser(friendId);

        return userFriendsStorage
                .add(user, friend)
                .stream()
                .map((f) -> userStorage.getUser(f.getId()).orElse(null))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> removeFriend(long userId, long friendId) {
        User user = findUser(userId);
        User friend = findUser(friendId);

        return userFriendsStorage
                .remove(user, friend)
                .stream()
                .map((f) -> userStorage.getUser(f.getId()).orElse(null))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getCommonFriends(long firstUserId, long secondUserId) {
        User firstUser = findUser(firstUserId);
        User secondUser = findUser(secondUserId);

        List<Long> firstUserFriends = userFriendsStorage.getUserFriends(firstUser);
        List<Long> secondUserFriends = userFriendsStorage.getUserFriends(secondUser);

        return firstUserFriends
                .stream()
                .filter(secondUserFriends::contains)
                .map(userId -> userStorage.getUser(userId).orElse(null))
                .collect(Collectors.toList());
    }

}
