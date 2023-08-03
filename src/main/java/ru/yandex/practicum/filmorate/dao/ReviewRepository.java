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

    void addLike(long userId, long reviewId);

    void removeLike(long userId, long reviewId);

    void addDislike(long userId, long reviewId);

    void removeDislike(long userId, long reviewId);

}
