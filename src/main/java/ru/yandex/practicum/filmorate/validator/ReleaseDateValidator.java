package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Calendar;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateConstraint, LocalDate> {

    @Override
    public void initialize(ReleaseDateConstraint releaseDate) {
    }

    @Override
    public boolean isValid(LocalDate releaseDate, ConstraintValidatorContext ctx) {
        if (releaseDate == null) {
            return true;
        }

        return releaseDate.isAfter(LocalDate.of(1895, Calendar.DECEMBER, 28));
    }

}
