package ru.khalilov.microservice.client.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.khalilov.microservice.common.exceptions.RemoteServiceException;
import ru.khalilov.microservice.common.constants.OwnerRabbitNames;
import ru.khalilov.microservice.common.methodEnums.OwnerMethod;
import ru.khalilov.microservice.common.models.OwnerDetails;
import ru.khalilov.microservice.common.requests.LoadUserDetailsRequest;

@Service
public class RemoteUserDetailsService implements UserDetailsService {
    private RabbitTemplate template;

    public RemoteUserDetailsService(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Object result = template.convertSendAndReceive(
                OwnerRabbitNames.MAIN_QUEUE_ROUTING_KEY,
                LoadUserDetailsRequest.builder().Username(username).build(),
                m -> {
                    m.getMessageProperties().getHeaders().put(OwnerRabbitNames.METHOD_NAMES_HEADER, OwnerMethod.LOAD_USER_DETAILS);
                    return m;
                }
        );

        System.out.println(result);
        if (!(result instanceof OwnerDetails)) {
            throw RemoteServiceException.InvalidResponseFormat();
        }

        return (OwnerDetails) result;
    }
}
