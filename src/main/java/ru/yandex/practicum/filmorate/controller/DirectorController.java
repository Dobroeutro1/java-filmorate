package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.DirectorService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/directors")
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping
    public List<Director> getAll() {
        log.info("GET-запрос к эндпоинту: '/directors'");
        return directorService.getAll();
    }

    @GetMapping("/{directorId}")
    public Director findDirector(@PathVariable("directorId") long directorId) {
        log.info("GET-запрос к эндпоинту: '/directors/{directorId}'");
        return directorService.findById(directorId);
    }

    @PostMapping
    public Director createDirector(@Valid @RequestBody Director director) {
        log.info("POST-запрос к эндпоинту: '/directors'");
        return directorService.create(director);
    }

    @PutMapping
    public Director updateDirector(@Valid @RequestBody Director director) {
        log.info("PUT-запрос к эндпоинту: '/directors'");
        return directorService.update(director);
    }

    @DeleteMapping("/{directorId}")
    public Director deleteDirector(@PathVariable("directorId") long directorId) {
        log.info("DELETE-запрос к эндпоинту: '/directors/{directorId}'");
        return directorService.delete(directorId);
    }

}
