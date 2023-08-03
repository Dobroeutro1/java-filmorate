package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getAll(Long filmId, Integer count);

    Review getReview(long reviewId);

    Review create(Review review);

    Review update(Review review);

    void remove(long reviewId);

    void addLike(long userId, long reviewId);

    void removeLike(long userId, long reviewId);

    void addDislike(long userId, long reviewId);

    void removeDislike(long userId, long reviewId);

}
