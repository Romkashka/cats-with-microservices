package ru.khalilov.microservice.client.util;

import ru.khalilov.microservice.common.exceptions.RemoteServiceException;

public class TimeoutExceptionThrower implements Runnable {
    @Override
    public void run() {
        throw RemoteServiceException.TimeoutExceeded();
    }
}
