package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User findUser(long userId);

    User create(User user);

    User update(User user);

    List<User> getUserFriends(long userId);

    List<User> addFriend(long userId, long friendId);

    List<User> removeFriend(long userId, long friendId);

    List<User> getCommonFriends(long firstUserId, long secondUserId);

}
