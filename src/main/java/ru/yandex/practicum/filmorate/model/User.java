package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
public class User {

    private long id;

    @NotNull
    @Email
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    private String login;

    @NotNull
    private String name;

    @NotNull
    @Past
    private LocalDate birthday;

}
