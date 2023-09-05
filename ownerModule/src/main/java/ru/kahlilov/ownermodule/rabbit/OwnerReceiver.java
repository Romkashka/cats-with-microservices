package ru.kahlilov.ownermodule.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.kahlilov.ownermodule.service.OwnerService;
import ru.khalilov.microservice.common.constants.OwnerRabbitNames;
import ru.khalilov.microservice.common.dtos.OwnerDto;
import ru.khalilov.microservice.common.methodEnums.OwnerMethod;
import ru.khalilov.microservice.common.requests.LoadUserDetailsRequest;
import ru.khalilov.microservice.common.requests.OwnerRelatedRequest;

import java.util.List;

@Component
@RabbitListener(queues = OwnerRabbitNames.MAIN_QUEUE_NAME)
public class OwnerReceiver {
    private final OwnerService ownerService;

    public OwnerReceiver(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RabbitHandler
    public List<OwnerDto> receive(@Header(OwnerRabbitNames.METHOD_NAMES_HEADER) OwnerMethod method, @Payload OwnerRelatedRequest request) {
        switch (method) {
            case GET -> {
                return List.of(ownerService.read(request.OwnerId()));
            }
            case UPDATE -> {
                return List.of(ownerService.update(request.OwnerId(), request.Name(), request.Surname()));
            }
            case DELETE -> {
                ownerService.deleteById(request.OwnerId());
                return List.of();
            }
            case CREATE -> {
                return List.of(ownerService.create(request.Name(), request.Surname(), request.Username(), request.Password()));
            }
            case ADD_CAT -> {
                return List.of(ownerService.addCat(request.OwnerId(), request.CatId()));
            }
            case REMOVE_CAT -> {
                return List.of(ownerService.removeCat(request.OwnerId(), request.CatId()));
            }
            case GET_ALL -> {
                return ownerService.findAll();
            }
            default -> {
                throw new IllegalArgumentException("No method connected to '" + method + "'");
            }
        }
    }

    @RabbitHandler
    public UserDetails receive(@Payload LoadUserDetailsRequest request) {
        return ownerService.loadUserByUsername(request.Username());
    }
}
