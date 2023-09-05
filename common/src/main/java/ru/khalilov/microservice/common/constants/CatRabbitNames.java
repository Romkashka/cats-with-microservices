package ru.khalilov.microservice.common.constants;

public class CatRabbitNames {
    public static final String MAIN_QUEUE_NAME = "cats_main";
    public static final String EXCEPTION_QUEUE_NAME = "cats_exceptions";
    public static final String EXCHANGE_NAME = "cats_exchange";
    public static final String METHOD_NAMES_HEADER = "method_name";
    public static final String MAIN_QUEUE_ROUTING_KEY = "cats_main";
    public static final String EXCEPTION_QUEUE_ROUTING_KEY = "cats_exception";
}
