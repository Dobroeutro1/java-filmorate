package ru.yandex.practicum.filmorate.exeption;

public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException(String message) {
        super(message);
    }

}
