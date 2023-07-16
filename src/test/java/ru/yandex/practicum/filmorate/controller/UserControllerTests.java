package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserControllerTests {

	// Инициализация Validator
	private static final Validator validator;

	static {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.usingContext().getValidator();
	}

	@Test
	@DisplayName("Создание пользователя с корректными данными")
	void shouldBeOkWhenCreateUserWithAllData() {
		User user = new User();

		user.setName("name");
		user.setLogin("login");
		user.setEmail("email@example.com");
		user.setBirthday(LocalDate.of(2023, Calendar.FEBRUARY, 1));

		Set<ConstraintViolation<User>> validates = validator.validate(user);

		assertEquals(0, validates.size());
	}

	@Test
	@DisplayName("Создание пользователя с пустым email")
	void shouldBeErrorWhenCreateUserWithEmailEmpty() {
		User user = new User();

		user.setName("name");
		user.setLogin("login");
		user.setBirthday(LocalDate.of(2023, Calendar.FEBRUARY, 1));

		Set<ConstraintViolation<User>> validates = validator.validate(user);

		assertTrue(validates.size() > 0);
	}

	@Test
	@DisplayName("Создание пользователя с невалидным email")
	void shouldBeErrorWhenCreateUserWithEmailNotValid() {
		User user = new User();

		user.setName("name");
		user.setLogin("login");
		user.setEmail("emailexample.com");
		user.setBirthday(LocalDate.of(2023, Calendar.FEBRUARY, 1));

		Set<ConstraintViolation<User>> validates = validator.validate(user);

		assertTrue(validates.size() > 0);
	}

	@Test
	@DisplayName("Создание пользователя с пустым логином")
	void shouldBeErrorWhenCreateUserWithLoginNull() {
		User user = new User();

		user.setName("name");
		user.setEmail("email@example.com");
		user.setBirthday(LocalDate.of(2023, Calendar.FEBRUARY, 1));

		Set<ConstraintViolation<User>> validates = validator.validate(user);

		assertTrue(validates.size() > 0);
	}

	@Test
	@DisplayName("Создание пользователя с пробелом в логине")
	void shouldBeErrorWhenCreateUserWithLoginSpace() {
		User user = new User();

		user.setName("name");
		user.setLogin(" ");
		user.setEmail("email@example.com");
		user.setBirthday(LocalDate.of(2023, Calendar.FEBRUARY, 1));

		Set<ConstraintViolation<User>> validates = validator.validate(user);

		assertTrue(validates.size() > 0);
	}

	@Test
	@DisplayName("Создание пользователя с пустой датой рождения")
	void shouldBeErrorWhenCreateUserWithBirthdayNull() {
		User user = new User();

		user.setName("name");
		user.setLogin("login");
		user.setEmail("email@example.com");

		Set<ConstraintViolation<User>> validates = validator.validate(user);

		assertTrue(validates.size() > 0);
	}

	@Test
	@DisplayName("Создание пользователя с датой рождения в будущем")
	void shouldBeErrorWhenCreateUserWithBirthdayFuture() {
		User user = new User();

		user.setName("name");
		user.setLogin("login");
		user.setEmail("email@example.com");
		user.setBirthday(LocalDate.of(2024, Calendar.FEBRUARY, 1));

		Set<ConstraintViolation<User>> validates = validator.validate(user);

		assertTrue(validates.size() > 0);
	}

}
