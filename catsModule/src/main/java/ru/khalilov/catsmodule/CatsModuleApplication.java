package ru.khalilov.catsmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("ru.khalilov.catsmodule.dao")
@EntityScan("ru.khalilov.catsmodule.dao.entities")
public class CatsModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatsModuleApplication.class, args);
	}

}
