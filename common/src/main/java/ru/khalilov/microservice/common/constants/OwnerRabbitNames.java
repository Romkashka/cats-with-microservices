package ru.khalilov.microservice.common.constants;

public class OwnerRabbitNames {
    public static final String MAIN_QUEUE_NAME = "owners_main";
    public static final String EXCEPTION_QUEUE_NAME = "owners_exceptions";
    public static final String EXCHANGE_NAME = "owners_exchange";
    public static final String METHOD_NAMES_HEADER = "method_name";
    public static final String MAIN_QUEUE_ROUTING_KEY = "owners_main";
    public static final String EXCEPTION_QUEUE_ROUTING_KEY = "owners_exception";
}
