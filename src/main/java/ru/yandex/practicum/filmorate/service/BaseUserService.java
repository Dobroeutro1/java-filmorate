package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserFriendsRepository;
import ru.yandex.practicum.filmorate.dao.UserRepository;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaseUserService implements UserService {

    private final UserRepository userRepository;
    private final UserFriendsRepository userFriendsRepository;

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User findUser(long userId) {
        return userRepository.getUser(userId).orElseThrow(() -> {
            log.info(String.format("Ошибка получения пользователя с id: %s. Пользователь не найден", userId));
            return new NotFoundException(String.format("Пользователь с id %s не найден", userId));
        });
    }

    @Override
    public User create(User user) {
        return userRepository.create(user);
    }

    @Override
    public User update(User user) {
        findUser(user.getId());

        return userRepository.update(user);
    }

    @Override
    public void delete(long id) {
        findUser(id);
        userRepository.deleteUser(id);
    }

    @Override
    public List<User> getUserFriends(long userId) {
        User user = userRepository.getUser(userId).orElseThrow(() -> {
            log.info(String.format("Ошибка получения пользователя с id: %s. Пользователь не найден", userId));
            return new NotFoundException(String.format("Пользователь с id %s не найден", userId));
        });

        return userFriendsRepository.getUserFriends(user);
    }

    @Override
    public void addFriend(long userId, long friendId) {
        User user = findUser(userId);
        User friend = findUser(friendId);

        userFriendsRepository.add(user.getId(), friend.getId());
    }

    @Override
    public void approveFriend(long userId, long friendId) {
        User user = findUser(userId);
        User friend = findUser(friendId);

        userFriendsRepository.approve(user.getId(), friend.getId());
    }

    @Override
    public void removeFriend(long userId, long friendId) {
        User user = findUser(userId);
        User friend = findUser(friendId);

        userFriendsRepository.remove(user.getId(), friend.getId());
    }

    @Override
    public List<User> getCommonFriends(long firstUserId, long secondUserId) {
        User firstUser = findUser(firstUserId);
        User secondUser = findUser(secondUserId);

        return userFriendsRepository.getCommonFriends(firstUser.getId(), secondUser.getId());
    }

}
