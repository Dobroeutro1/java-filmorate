package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Component
public class InMemoryUserFriendsStorage implements UserFriendsStorage {

    private final HashMap<Long, Set<Long>> userFriendsMap = new HashMap<>();

    @Override
    public Set<Long> getUserFriends(User user) {
        return userFriendsMap.computeIfAbsent(user.getId(), id -> new HashSet<>());
    }

    @Override
    public Set<Long> add(User user, User friend) {
        Set<Long> userFriendsIds = userFriendsMap.computeIfAbsent(user.getId(), id -> new HashSet<>());
        userFriendsIds.add(friend.getId());

        Set<Long> friendFriendsIds = userFriendsMap.computeIfAbsent(friend.getId(), id -> new HashSet<>());
        friendFriendsIds.add(user.getId());

        return userFriendsIds;
    }

    @Override
    public Set<Long> remove(User user, User friend) {
        Set<Long> userFriendsIds = userFriendsMap.computeIfAbsent(user.getId(), id -> new HashSet<>());
        userFriendsIds.remove(friend.getId());

        Set<Long> friendFriendsIds = userFriendsMap.computeIfAbsent(friend.getId(), id -> new HashSet<>());
        friendFriendsIds.remove(user.getId());

        return userFriendsIds;
    }

}
