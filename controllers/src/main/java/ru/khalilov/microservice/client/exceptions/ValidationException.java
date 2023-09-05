package ru.khalilov.microservice.client.exceptions;

import java.time.LocalDate;

public class ValidationException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    protected ValidationException(String message) {
        super(message);
    }

    public static ValidationException birthdayInFuture(LocalDate birthday) {
        return new ValidationException("Birthday must not be after today (" + LocalDate.now() + "), but " + birthday + " was found");
    }
}
