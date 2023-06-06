package ru.yandex.practicum.filmorate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Set;

@SpringBootTest
class FilmControllerTests {

	// Инициализация Validator
	private static final Validator validator;

	static {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.usingContext().getValidator();
	}

	@Test
	@DisplayName("Создание фильма с корректными данными")
	void shouldBeOkWhenCreateFilmWithAllData() {
		Film film = new Film();

		film.setName("name");
		film.setDescription("description");
		film.setDuration(60);
		film.setReleaseDate(LocalDate.of(2023, Calendar.FEBRUARY, 1));

		Set<ConstraintViolation<Film>> validates = validator.validate(film);

		assertEquals(0, validates.size());
	}

	@Test
	@DisplayName("Создание фильма с пустым именем")
	void shouldBeErrorWhenCreatingFilmWithoutName() {
		Film film = new Film();

		film.setName("");
		film.setDescription("description");
		film.setDuration(60);
		film.setReleaseDate(LocalDate.of(2023, Calendar.FEBRUARY, 1));

		Set<ConstraintViolation<Film>> validates = validator.validate(film);

		assertTrue(validates.size() > 0);
	}

	@Test
	@DisplayName("Создание фильма без имени")
	void shouldBeErrorWhenCreatingFilmWithNameNull() {
		Film film = new Film();

		film.setDescription("description");
		film.setDuration(60);
		film.setReleaseDate(LocalDate.of(2023, Calendar.FEBRUARY, 1));

		Set<ConstraintViolation<Film>> validates = validator.validate(film);

		assertEquals(2, validates.size());
	}

	@Test
	@DisplayName("Создание фильма без описания")
	void shouldBeErrorWhenCreatingFilmWithDescriptionNull() {
		Film film = new Film();

		film.setName("name");
		film.setDuration(60);
		film.setReleaseDate(LocalDate.of(2023, Calendar.FEBRUARY, 1));

		Set<ConstraintViolation<Film>> validates = validator.validate(film);

		assertTrue(validates.size() > 0);
	}

	@Test
	@DisplayName("Создание фильма с описанием больше 200 символов")
	void shouldBeErrorWhenCreatingFilmWithDescriptionLengthMore200Char() {
		Film film = new Film();
		String description = "a".repeat(201);

		film.setName("name");
		film.setDescription(description);
		film.setDuration(60);
		film.setReleaseDate(LocalDate.of(2023, Calendar.FEBRUARY, 1));

		Set<ConstraintViolation<Film>> validates = validator.validate(film);

		assertTrue(validates.size() > 0);
	}

	@Test
	@DisplayName("Создание фильма с пустой датой релиза")
	void shouldBeErrorWhenCreatingFilmWithReleaseDateNull() {
		Film film = new Film();

		film.setName("name");
		film.setDescription("description");
		film.setDuration(60);

		Set<ConstraintViolation<Film>> validates = validator.validate(film);

		assertTrue(validates.size() > 0);
	}

	@Test
	@DisplayName("Создание фильма с датой релиза раньше, чем 28 декабря 1895 года")
	void shouldBeErrorWhenCreatingFilmWithReleaseDateEarlierThanBirthdayCinema() {
		Film film = new Film();

		film.setName("name");
		film.setDescription("description");
		film.setDuration(60);
		film.setReleaseDate(LocalDate.of(1894, Calendar.FEBRUARY, 1));

		Set<ConstraintViolation<Film>> validates = validator.validate(film);

		assertTrue(validates.size() > 0);
	}

	@Test
	@DisplayName("Создание фильма с пустой продолжительностью")
	void shouldBeErrorWhenCreatingFilmWithDurationNull() {
		Film film = new Film();

		film.setName("name");
		film.setDescription("description");
		film.setReleaseDate(LocalDate.of(2023, Calendar.FEBRUARY, 1));

		Set<ConstraintViolation<Film>> validates = validator.validate(film);

		System.out.println("AAAAAAA: " + validates);

		assertTrue(validates.size() > 0);
	}

	@Test
	@DisplayName("Создание фильма с отрицательной продолжительностью")
	void shouldBeErrorWhenCreatingFilmWithDurationNegative() {
		Film film = new Film();

		film.setName("name");
		film.setDescription("description");
		film.setDuration(-60);
		film.setReleaseDate(LocalDate.of(2023, Calendar.FEBRUARY, 1));

		Set<ConstraintViolation<Film>> validates = validator.validate(film);

		assertTrue(validates.size() > 0);
	}

}
