package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserFriendsRepository {

    List<User> getUserFriends(User user);

    List<User> getCommonFriends(long fUserId, long sUserId);

    void add(long userId, long friendId);

    void approve(long userId, long friendId);

    void remove(long userId, long friendId);

}
