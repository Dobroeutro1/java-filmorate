package ru.yandex.practicum.filmorate.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.dao.impl.UserRepositoryImpl;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Sql(scripts = "/init_users.sql")
@Import(UserRepositoryImpl.class)
public class UserRepositoryImplTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetAll() {
        List<User> users = userRepository.getAll();

        assertEquals(3, users.size());
        assertEquals(1, users.get(0).getId());
        assertEquals("Artem", users.get(0).getName());
        assertEquals("artem@email.com", users.get(0).getEmail());
        assertEquals("artem", users.get(0).getLogin());
        assertEquals(LocalDate.of(1998, 1, 6), users.get(0).getBirthday());
    }

    @Test
    public void testGetUser() {
        Optional<User> userOpt = userRepository.getUser(1);

        assertFalse(userOpt.isEmpty());

        User user = userOpt.get();

        assertEquals(1, user.getId());
        assertEquals("Artem", user.getName());
        assertEquals("artem@email.com", user.getEmail());
        assertEquals("artem", user.getLogin());
        assertEquals(LocalDate.of(1998, 1, 6), user.getBirthday());
    }

    @Test
    public void testCreateUser() {
        User user = new User(0, "email@email.com", "login", "name",
                LocalDate.of(2000, 1, 1));

        User createdUser = userRepository.create(user);

        assertNotNull(createdUser);
        assertEquals(4, createdUser.getId());
        assertEquals("name", createdUser.getName());
        assertEquals("email@email.com", createdUser.getEmail());
        assertEquals("login", createdUser.getLogin());
        assertEquals(LocalDate.of(2000, 1, 1), createdUser.getBirthday());
    }

    @Test
    public void testUpdateUser() {
        User user = new User(1, "email@email.com", "login", "name",
                LocalDate.of(2000, 1, 1));

        User updatedUser = userRepository.update(user);

        assertNotNull(updatedUser);
        assertEquals(1, updatedUser.getId());
        assertEquals("name", updatedUser.getName());
        assertEquals("email@email.com", updatedUser.getEmail());
        assertEquals("login", updatedUser.getLogin());
        assertEquals(LocalDate.of(2000, 1, 1), updatedUser.getBirthday());
    }
}
