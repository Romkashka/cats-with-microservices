package ru.kahlilov.ownermodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("ru.kahlilov.ownermodule.dao")
@EntityScan("ru.kahlilov.ownermodule.dao.entities")
public class OwnerModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(OwnerModuleApplication.class, args);
    }

}
