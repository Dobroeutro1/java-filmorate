package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserFriendsStorage {

    List<Long> getUserFriends(User user);

    List<Friend> add(User user, User friend);

    List<Friend> remove(User user, User friend);

}
