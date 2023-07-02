package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserFriendsStorage implements UserFriendsStorage {

    private final HashMap<Long, List<Friend>> userFriendsMap = new HashMap<>();

    @Override
    public List<Long> getUserFriends(User user) {
        return userFriendsMap
                .computeIfAbsent(user.getId(), id -> new ArrayList<>())
                .stream()
                .filter(Friend::isApprove)
                .map(Friend::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Friend> add(User user, User friend) {
        List<Friend> userFriendsIds = userFriendsMap.computeIfAbsent(user.getId(), id -> new ArrayList<>());
        userFriendsIds.add(new Friend(friend.getId(), true));

        List<Friend> friendFriendsIds = userFriendsMap.computeIfAbsent(friend.getId(), id -> new ArrayList<>());
        friendFriendsIds.add(new Friend(user.getId(), true));

        return userFriendsIds;
    }

    @Override
    public List<Friend> remove(User user, User friend) {
        List<Friend> userFriendsIds = userFriendsMap.computeIfAbsent(user.getId(), id -> new ArrayList<>());
        userFriendsMap.put(user.getId(), userFriendsIds
                .stream()
                .filter(f -> f.getId() != friend.getId())
                .collect(Collectors.toList()));

        List<Friend> friendFriendsIds = userFriendsMap.computeIfAbsent(friend.getId(), id -> new ArrayList<>());
        userFriendsMap.put(friend.getId(), friendFriendsIds
                .stream()
                .filter(f -> f.getId() != user.getId())
                .collect(Collectors.toList()));

        return userFriendsIds;
    }

}
