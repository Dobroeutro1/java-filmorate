package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    private long reviewId;

    @NotNull
    @NotBlank
    @NotEmpty
    private String content;

    @NotNull
    private Boolean isPositive;

    @NotNull
    private Long userId;

    @NotNull
    private Long filmId;

    @NotNull
    private int useful;

}
