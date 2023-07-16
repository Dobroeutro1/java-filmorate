package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.dao.impl.UserFriendsRepositoryImpl;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Sql(scripts = "/init_users.sql")
@Import(UserFriendsRepositoryImpl.class)
public class UserFriendsRepositoryImplTests {

    @Autowired
    UserFriendsRepository userFriendsRepository;

    @Test
    public void testAddFriend() {
        userFriendsRepository.add(1, 2);
        userFriendsRepository.approve(2, 1);

        User user = new User(1, "name", "name", "name", LocalDate.of(2000, 1, 1));

        List<User> users = userFriendsRepository.getUserFriends(user);

        assertEquals(1, users.size());
        assertEquals(2, users.get(0).getId());
        assertEquals("Andrey", users.get(0).getName());
        assertEquals("andrey@email.com", users.get(0).getEmail());
        assertEquals("andrey", users.get(0).getLogin());
        assertEquals(LocalDate.of(2000, 1, 10), users.get(0).getBirthday());
    }

    @Test
    public void testRemoveFriend() {
        userFriendsRepository.add(1, 2);
        userFriendsRepository.approve(2, 1);
        userFriendsRepository.remove(1, 2);

        User user = new User(1, "name", "name", "name", LocalDate.of(2000, 1, 1));

        List<User> users = userFriendsRepository.getUserFriends(user);

        assertEquals(0, users.size());
    }

    @Test
    public void testGetCommonFriend() {
        userFriendsRepository.add(1, 2);
        userFriendsRepository.approve(2, 1);
        userFriendsRepository.add(1, 3);
        userFriendsRepository.approve(3, 1);

        List<User> users = userFriendsRepository.getCommonFriends(2, 3);

        assertEquals(1, users.size());
        assertEquals(1, users.get(0).getId());
        assertEquals("Artem", users.get(0).getName());
        assertEquals("artem@email.com", users.get(0).getEmail());
        assertEquals("artem", users.get(0).getLogin());
        assertEquals(LocalDate.of(1998, 1, 6), users.get(0).getBirthday());
    }
}
