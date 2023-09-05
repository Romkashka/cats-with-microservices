package ru.khalilov.microservice.client.exceptions;

public class GeneralClientException extends RuntimeException {
    protected GeneralClientException(String message) {
        super(message);
    }
}
