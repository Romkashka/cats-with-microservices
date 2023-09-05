package ru.khalilov.microservice.common.exceptions;

public class ServiceException extends GeneralServiceException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    protected ServiceException(String message) {
        super(message);
    }

    public static ServiceException nothingToUpdate() {
        return new ServiceException("Operation can't be executed: nothing to update");
    }

    public static ServiceException noEntityWithGivenId(String entityType, long id) {
        return new ServiceException("No entity of type '" + entityType + "' with id '" + id + "' was found");
    }
}
