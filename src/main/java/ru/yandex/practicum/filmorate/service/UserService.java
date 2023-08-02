package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User findUser(long userId);

    User create(User user);

    User update(User user);

    void delete(long id);

    List<User> getUserFriends(long userId);

    void addFriend(long userId, long friendId);

    void approveFriend(long userId, long friendId);

    void removeFriend(long userId, long friendId);

    List<User> getCommonFriends(long firstUserId, long secondUserId);

}
