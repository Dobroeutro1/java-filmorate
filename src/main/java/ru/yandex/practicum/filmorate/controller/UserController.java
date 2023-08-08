package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.impl.BaseUserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final BaseUserService service;

    @GetMapping()
    public List<User> getAll() {
        log.info("GET-запрос к эндпоинту: '/users'");
        return service.getAll();
    }

    @GetMapping("/{userId}")
    public User findUser(@PathVariable("userId") long userId) {
        log.info("GET-запрос к эндпоинту: '/users/{userId}'");
        return service.findUser(userId);
    }

    @PostMapping()
    public User create(@Valid @RequestBody User user) {
        log.info("POST-запрос к эндпоинту: '/users'");
        return service.create(user);
    }

    @PutMapping()
    public User update(@Valid @RequestBody User user) {
        log.info("PUT-запрос к эндпоинту: '/users'");
        return service.update(user);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") long userId) {
        log.info("DELETE-запрос к эндпоинту: '/users/{userId}");
        service.delete(userId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> getUserFriends(@PathVariable("userId") long userId) {
        log.info("GET-запрос к эндпоинту: '/users/{userId}/friends'");
        return service.getUserFriends(userId);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addUserFriend(@PathVariable("userId") long userId, @PathVariable("friendId") long friendId) {
        log.info("PUT-запрос к эндпоинту: '/users/{userId}/friends/{friendId}'");
        service.addFriend(userId, friendId);
    }

    @PutMapping("/{userId}/friends/{friendId}/approve")
    public void approveUserFriend(@PathVariable("userId") long userId, @PathVariable("friendId") long friendId) {
        log.info("PUT-запрос к эндпоинту: '/users/{userId}/friends/{friendId}/approve'");
        service.approveFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void removeUserFriend(@PathVariable("userId") long userId, @PathVariable("friendId") long friendId) {
        log.info("DELETE-запрос к эндпоинту: '/users/{userId}/friends/{friendId}'");
        service.removeFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("userId") long userId, @PathVariable("otherId") long otherId) {
        log.info("GET-запрос к эндпоинту: '/users/{userId}/friends/common/{otherId}");
        return service.getCommonFriends(userId, otherId);
    }

    @GetMapping("/{id}/feed")
    public List<Event> getFeed(@PathVariable("id") long id) {
        log.info("GET-запрос к эндпоинту: '/user/{id}/feed");
        return service.getNewsFeed(id);
    }

    @GetMapping("/{userId}/recommendations")
    public List<Film> getRecommendations(@PathVariable("userId") long userId) {
        log.info("GET-запрос к эндпоинту: '/users/{userId}/recommendations");
        return service.getRecommendations(userId);
    }

}
