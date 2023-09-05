package ru.khalilov.microservice.common.util;

import lombok.NonNull;
import ru.khalilov.microservice.common.exceptions.CatException;
import ru.khalilov.microservice.common.exceptions.OwnerException;

public class Validation {
    public static @NonNull String validateOwnerName(@NonNull String name) {
        if (!name.chars().allMatch((c) -> Character.getType(c) == Character.UPPERCASE_LETTER ||
                Character.getType(c) == Character.LOWERCASE_LETTER)) {
            throw OwnerException.invalidName(name);
        }

        return name;
    }

    public static @NonNull String validateCatName(@NonNull String name) {
        if (!name.chars().allMatch((c) -> Character.getType(c) == Character.UPPERCASE_LETTER ||
                Character.getType(c) == Character.LOWERCASE_LETTER)) {
            throw CatException.invalidName(name);
        }

        return name;
    }

    public static @NonNull String validateBreed(@NonNull String breed) {
        if (!breed.chars().allMatch((letter) -> Character.getType(letter) == Character.LOWERCASE_LETTER)) {
            throw CatException.invalidBreed(breed);
        }

        return breed;
    }
}
