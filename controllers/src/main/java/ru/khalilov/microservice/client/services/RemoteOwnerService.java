package ru.khalilov.microservice.client.services;

import lombok.NonNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.khalilov.microservice.common.exceptions.RemoteServiceException;
import ru.khalilov.microservice.common.constants.OwnerRabbitNames;
import ru.khalilov.microservice.common.dtos.OwnerDto;
import ru.khalilov.microservice.common.methodEnums.OwnerMethod;
import ru.khalilov.microservice.common.requests.OwnerRelatedRequest;

import java.util.List;

@Service
public class RemoteOwnerService {
    private RabbitTemplate template;

    public RemoteOwnerService(RabbitTemplate template) {
        this.template = template;
    }

    public OwnerDto create(@NonNull String name, @NonNull String surname, @NonNull String username, @NonNull String password) {
        OwnerRelatedRequest request = OwnerRelatedRequest.builder().Name(name).Surname(surname).Username(username).Password(password).build();
        return extractFirstDtoFromResult(convertSendAndReceive(request, OwnerMethod.CREATE));
    }

    public OwnerDto read(long id) {
        OwnerRelatedRequest request = OwnerRelatedRequest.builder().OwnerId(id).build();
        return extractFirstDtoFromResult(convertSendAndReceive(request, OwnerMethod.GET));
    }

    public OwnerDto update(long id, String name, String surname) {
        OwnerRelatedRequest request = OwnerRelatedRequest.builder().OwnerId(id).Name(name).Surname(surname).build();
        return extractFirstDtoFromResult(convertSendAndReceive(request, OwnerMethod.UPDATE));
    }

    public void deleteById(long id) {
        OwnerRelatedRequest request = OwnerRelatedRequest.builder().OwnerId(id).build();
        tryConvertToOwnerDtoList(convertSendAndReceive(request, OwnerMethod.DELETE));
    }

    public OwnerDto addCat(long ownerId, long catId) {
        OwnerRelatedRequest request = OwnerRelatedRequest.builder().OwnerId(ownerId).CatId(catId).build();
        return extractFirstDtoFromResult(convertSendAndReceive(request, OwnerMethod.ADD_CAT));
    }

    public OwnerDto removeCat(long ownerId, long catId) {
        OwnerRelatedRequest request = OwnerRelatedRequest.builder().OwnerId(ownerId).CatId(catId).build();
        return extractFirstDtoFromResult(convertSendAndReceive(request, OwnerMethod.REMOVE_CAT));
    }

    public @NonNull List<OwnerDto> findAll() {
        return tryConvertToOwnerDtoList(convertSendAndReceive(OwnerRelatedRequest.builder().build(), OwnerMethod.GET_ALL));
    }

    private Object convertSendAndReceive(OwnerRelatedRequest request, OwnerMethod method) {
        return template.convertSendAndReceive(
                OwnerRabbitNames.MAIN_QUEUE_ROUTING_KEY,
                request,
                m -> {
                    m.getMessageProperties().getHeaders().put(OwnerRabbitNames.METHOD_NAMES_HEADER, method);
                    return m;
                }
        );
    }

    private List<OwnerDto> tryConvertToOwnerDtoList(Object originalResult) {
        if (!(originalResult instanceof List<?> listResult)) {
            throw RemoteServiceException.InvalidResponseFormat();
        }

        if (!listResult.isEmpty() && !(listResult.get(0) instanceof OwnerDto)) {
            throw RemoteServiceException.InvalidResponseFormat();
        }

        return (List<OwnerDto>) listResult;
    }

    private OwnerDto extractFirstDtoFromResult(Object originalResult) {
        return tryConvertToOwnerDtoList(originalResult).get(0);
    }
}
