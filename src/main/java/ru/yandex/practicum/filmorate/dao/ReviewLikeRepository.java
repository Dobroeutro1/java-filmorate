package ru.yandex.practicum.filmorate.dao;

public interface ReviewLikeRepository {

    Boolean isHaveUserLike(Long reviewId, Long userId);

    void add(Long reviewId, Long userId, Boolean isLike);

    void remove(Long reviewId, Long userId);

}
