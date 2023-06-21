package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Set;

public interface UserFriendsStorage {

    Set<Long> getUserFriends(User user);

    Set<Long> add(User user, User friend);

    Set<Long> remove(User user, User friend);

}
