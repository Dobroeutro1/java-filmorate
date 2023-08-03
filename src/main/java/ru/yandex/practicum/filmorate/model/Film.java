package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.lang.Nullable;
import ru.yandex.practicum.filmorate.validator.ReleaseDateConstraint;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Film {

    private long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @Size(max = 200)
    private String description;

    @NotNull
    @ReleaseDateConstraint
    private LocalDate releaseDate;

    @NotNull
    @Positive
    private int duration;

    @NotNull
    private MPA mpa;

    @Nullable
    private Set<Genre> genres;
}
