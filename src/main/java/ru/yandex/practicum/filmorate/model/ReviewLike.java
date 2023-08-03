package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewLike {

    private long reviewId;

    @NotNull
    private Boolean isLike;

    @NotNull
    private Long userId;

}
