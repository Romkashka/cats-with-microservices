package ru.khalilov.microservice.common.exceptions;

public class RemoteServiceException extends RuntimeException {
    protected RemoteServiceException(String message) {
        super(message);
    }

    public static RemoteServiceException InvalidResponseFormat() {
        return new RemoteServiceException("Remote server error: unexpected response object type");
    }

    public static RemoteServiceException TimeoutExceeded() {
        return new RemoteServiceException("No response in set timeout");
    }
}
