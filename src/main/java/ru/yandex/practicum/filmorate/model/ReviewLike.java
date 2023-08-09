package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ReviewLike {

    private long reviewId;

    @NotNull
    private Boolean isLike;

    @NotNull
    private Long userId;

}
