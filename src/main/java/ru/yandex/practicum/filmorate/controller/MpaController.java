package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.impl.BaseMpaService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaController {

    private final BaseMpaService service;

    @GetMapping()
    public List<MPA> getAll() {
        log.info("GET-запрос к эндпоинту: '/mpa'");
        return service.getAll();
    }

    @GetMapping("/{mpaId}")
    public MPA findById(@PathVariable("mpaId") long mpaId) {
        log.info("GET-запрос к эндпоинту: '/mpa/{mpaId}'");
        return service.findById(mpaId);
    }

}
