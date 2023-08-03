package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.ReviewLikeRepository;
import ru.yandex.practicum.filmorate.dao.ReviewRepository;
import ru.yandex.practicum.filmorate.exeption.AlreadyExistException;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaseReviewService implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    final BaseUserService userService;
    final BaseFilmService filmService;

    @Override
    public List<Review> getAll(Long filmId, Integer count) {
        return reviewRepository.getAll(filmId, count);
    }

    @Override
    public Review getReview(long reviewId) {
        return reviewRepository.getReview(reviewId).orElseThrow(() -> {
            log.info(String.format("Ошибка получения отзыва с id: %s. Отзыв не найден", reviewId));
            return new NotFoundException(String.format("Отзыв с id %s не найден", reviewId));
        });
    }

    @Override
    public Review create(Review review) {
        userService.findUser(review.getUserId());
        filmService.findFilm(review.getFilmId());

        return reviewRepository.create(review);
    }

    @Override
    public Review update(Review review) {
        userService.findUser(review.getUserId());
        filmService.findFilm(review.getFilmId());

        getReview(review.getReviewId());

        reviewRepository.update(review);

        return getReview(review.getReviewId());
    }

    @Override
    public void remove(long reviewId) {
        reviewRepository.remove(reviewId);
    }

    @Override
    public void addLike(long userId, long reviewId) {
        boolean isHaveUserLike = reviewLikeRepository.isHaveUserLike(reviewId, userId);

        if (isHaveUserLike) {
            throw new AlreadyExistException(String.format("Пользователь с id %s уже поставил оценку фильму", userId));
        }

        reviewRepository.addRating(userId, reviewId);
        reviewLikeRepository.add(reviewId, userId, true);
    }

    @Override
    public void removeLike(long userId, long reviewId) {
        boolean isHaveUserLike = reviewLikeRepository.isHaveUserLike(reviewId, userId);

        if (!isHaveUserLike) {
            throw new AlreadyExistException(String.format("Пользователь с id %s не ставил оценку фильму", userId));
        }

        reviewRepository.removeRating(userId, reviewId);
        reviewLikeRepository.remove(reviewId, userId);
    }

    @Override
    public void addDislike(long userId, long reviewId) {
        boolean isHaveUserLike = reviewLikeRepository.isHaveUserLike(reviewId, userId);

        if (isHaveUserLike) {
            throw new AlreadyExistException(String.format("Пользователь с id %s уже поставил оценку фильму", userId));
        }

        reviewRepository.removeRating(userId, reviewId);
        reviewLikeRepository.add(reviewId, userId, false);
    }

    @Override
    public void removeDislike(long userId, long reviewId) {
        boolean isHaveUserLike = reviewLikeRepository.isHaveUserLike(reviewId, userId);

        if (!isHaveUserLike) {
            throw new AlreadyExistException(String.format("Пользователь с id %s не ставил оценку фильму", userId));
        }

        reviewRepository.addRating(userId, reviewId);
        reviewLikeRepository.remove(reviewId, userId);
    }

}
