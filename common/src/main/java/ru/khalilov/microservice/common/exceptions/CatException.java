package ru.khalilov.microservice.common.exceptions;

import lombok.NonNull;

public class CatException extends NestedDaoException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    protected CatException(String message) {
        super(message);
    }

    public static @NonNull CatException invalidName(@NonNull String name) {
        return new CatException("String '" + name + "' is not a valid cat name");
    }

    public static @NonNull CatException invalidBreed(@NonNull String breed) {
        return new CatException("String '" + breed + "' is not a valid cat breed");
    }
}
