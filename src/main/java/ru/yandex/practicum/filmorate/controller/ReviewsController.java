package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.impl.BaseReviewService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewsController {

    private final BaseReviewService reviewService;

    @GetMapping()
    public List<Review> getAll(@RequestParam(required = false) Long filmId,
                               @RequestParam(defaultValue = "10", required = false) Integer count) {
        log.info("GET-запрос к эндпоинту: '/reviews?filmId={filmId}&count={count}'");
        return reviewService.getAll(filmId, count);
    }

    @GetMapping("/{id}")
    public Review getReview(@PathVariable("id") long id) {
        log.info("GET-запрос к эндпоинту: '/reviews/{id}'");
        return reviewService.getReview(id);
    }

    @PostMapping()
    public Review create(@Valid @RequestBody Review review) {
        log.info("POST-запрос к эндпоинту: '/reviews'");
        return reviewService.create(review);
    }

    @PutMapping()
    public Review update(@Valid @RequestBody Review review) {
        log.info("PUT-запрос к эндпоинту: '/reviews'");
        return reviewService.update(review);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") long id) {
        log.info("DELETE-запрос к эндпоинту: '/reviews/{id}'");
        reviewService.remove(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") long id, @PathVariable("userId") long userId) {
        log.info("PUT-запрос к эндпоинту: '/reviews/{id}/like/{userId}'");
        reviewService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") long id, @PathVariable("userId") long userId) {
        log.info("DELETE-запрос к эндпоинту: '/reviews/{id}/like/{userId}'");
        reviewService.removeLike(id, userId);
    }

    @PutMapping("/{id}/dislike/{userId}")
    public void addDislike(@PathVariable("id") long id, @PathVariable("userId") long userId) {
        log.info("PUT-запрос к эндпоинту: '/reviews/{id}/dislike/{userId}'");
        reviewService.addDislike(id, userId);
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public void removeDislike(@PathVariable("id") long id, @PathVariable("userId") long userId) {
        log.info("PUT-запрос к эндпоинту: '/reviews/{id}/dislike/{userId}'");
        reviewService.removeDislike(id, userId);
    }

}
