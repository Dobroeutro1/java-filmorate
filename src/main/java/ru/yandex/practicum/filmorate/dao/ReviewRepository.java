package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {

    List<Review> getAll(Long filmId, Integer count);

    Optional<Review> getReview(long reviewId);

    Review create(Review item);

    Review update(Review item);

    void remove(long reviewId);

    void addRating(long userId, long reviewId);

    void removeRating(long userId, long reviewId);

}
