package ru.khalilov.catsmodule.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.khalilov.catsmodule.service.CatService;
import ru.khalilov.microservice.common.constants.CatRabbitNames;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class CatRabbitConfig {
    private final CatService catService;

    public CatRabbitConfig(CatService catService) {
        this.catService = catService;
    }

    @Bean
    public Queue mainQueue() {
        return new Queue(CatRabbitNames.MAIN_QUEUE_NAME);
    }

    @Bean
    public Queue exceptionQueue() {
        return new Queue(CatRabbitNames.EXCEPTION_QUEUE_NAME);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(CatRabbitNames.EXCHANGE_NAME);
    }

    @Bean
    public Binding mainQueueBinding(DirectExchange exchange, Queue mainQueue) {
        return BindingBuilder.bind(mainQueue).to(exchange).with(CatRabbitNames.MAIN_QUEUE_ROUTING_KEY);
    }

    @Bean
    public Binding exceptionQueueBinding(DirectExchange exchange, Queue exceptionQueue) {
        return BindingBuilder.bind(exceptionQueue).to(exchange).with(CatRabbitNames.EXCEPTION_QUEUE_ROUTING_KEY);
    }

    @Bean
    public CatReceiver receiver() {
        return new CatReceiver(catService);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter(objectMapper()));
        return rabbitTemplate;
    }
}
