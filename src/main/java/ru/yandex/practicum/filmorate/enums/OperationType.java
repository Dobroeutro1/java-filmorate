package ru.yandex.practicum.filmorate.enums;

import ru.yandex.practicum.filmorate.exeption.NotFoundException;

public enum OperationType {
    REMOVE,
    ADD,
    UPDATE;

    @Override
    public String toString() {
        return this.name();
    }

    public static OperationType toEnum(String s) {
        for (OperationType e : OperationType.values()) {
            if (e.name().equals(s)) return e;
        }
        throw new NotFoundException("ошибка приведения");
    }
}
