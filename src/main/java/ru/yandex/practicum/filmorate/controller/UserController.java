package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/users")
public class UserController {
    UserStorage storage = new UserStorage();

    @GetMapping()
    public List<User> getAll() {
        log.info("GET-запрос к эндпоинту: '/users'");
        return storage.getAll();
    }

    @PostMapping()
    public User create(@Valid @RequestBody User user) {
        log.info("POST-запрос к эндпоинту: '/users'");
        return storage.create(user);
    }

    @PutMapping()
    public User update(@Valid @RequestBody User user) {
        log.info("PUT-запрос к эндпоинту: '/users'");
        return storage.update(user);
    }
}
