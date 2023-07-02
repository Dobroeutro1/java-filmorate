package ru.yandex.practicum.filmorate.model;

import lombok.Getter;

@Getter
public enum MPA {
    G ("G"),
    PG ("PG"),
    PG_THIRTEEN ("PG-13"),
    R ("R"),
    NC_SEVENTEEN ("NC-17");

    private final String title;

    MPA(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "MPA{" +
                "title='" + title + '\'' +
                '}';
    }
}
