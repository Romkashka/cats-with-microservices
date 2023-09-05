package ru.khalilov.microservice.common.exceptions;

public class CredentialsException extends GeneralServiceException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    protected CredentialsException(String message) {
        super(message);
    }

    public static CredentialsException usernameAlreadyExists() {
        return new CredentialsException("This username is already exists. Please, choose another");
    }
}
