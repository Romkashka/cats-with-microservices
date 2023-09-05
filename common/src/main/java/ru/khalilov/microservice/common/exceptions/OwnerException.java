package ru.khalilov.microservice.common.exceptions;

import lombok.NonNull;

public class OwnerException extends NestedDaoException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    protected OwnerException(String message) {
        super(message);
    }

    public static @NonNull OwnerException invalidName(@NonNull String name) {
        return new OwnerException("String '" + name + "' is not a valid owner name");
    }
}
