package ru.khalilov.microservice.client.util;

import ru.khalilov.microservice.client.exceptions.ValidationException;

import java.time.LocalDate;

public class Validation {
    public static LocalDate validateBirthday(LocalDate birthDay) {
        if (birthDay.isAfter(LocalDate.now())) {
            throw ValidationException.birthdayInFuture(birthDay);
        }

        return birthDay;
    }
}
