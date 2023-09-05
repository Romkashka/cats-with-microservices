package ru.khalilov.microservice.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class ControllersApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControllersApplication.class, args);
    }

}
