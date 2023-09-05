package ru.kahlilov.ownermodule.rabbit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kahlilov.ownermodule.service.OwnerService;

@Configuration
public class OwnerRabbitConfig {
    private final OwnerService ownerService;

    @Autowired
    public OwnerRabbitConfig(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Bean
    public OwnerReceiver receiver() {
        return new OwnerReceiver(ownerService);
    }
}
