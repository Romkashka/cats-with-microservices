package ru.kahlilov.ownermodule.rabbit;

import lombok.NonNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.khalilov.microservice.common.constants.CatRabbitNames;
import ru.khalilov.microservice.common.dtos.CatDto;
import ru.khalilov.microservice.common.exceptions.RemoteServiceException;
import ru.khalilov.microservice.common.methodEnums.CatMethod;
import ru.khalilov.microservice.common.models.Color;
import ru.khalilov.microservice.common.requests.CatRelatedRequest;

import java.time.LocalDate;
import java.util.List;

@Service
public class InterServerRequestSender {
    private final RabbitTemplate template;

    public InterServerRequestSender(RabbitTemplate template) {
        this.template = template;
    }

    public CatDto read(long id) {
        CatRelatedRequest request = CatRelatedRequest.builder().CatId(id).build();
        return extractFirstDtoFromResult(convertSendAndReceive(request, CatMethod.GET_CAT));
    }

    public CatDto update(long id, String name, LocalDate birthDay, String breed, Color color) {
        CatRelatedRequest request = CatRelatedRequest
                .builder()
                .CatId(id)
                .Name(name)
                .Birthday(birthDay)
                .Breed(breed)
                .Color(color)
                .build();

        return extractFirstDtoFromResult(convertSendAndReceive(request, CatMethod.UPDATE));
    }

    public CatDto setOwner(long catId, Long ownerId) {
        CatRelatedRequest request = CatRelatedRequest.builder().CatId(catId).OwnerId(ownerId).build();
        return extractFirstDtoFromResult(convertSendAndReceive(request, CatMethod.SET_OWNER));
    }

    public @NonNull List<CatDto> filterByOwner(Long ownerId) {
        CatRelatedRequest request = CatRelatedRequest.builder().OwnerId(ownerId).build();
        return tryConvertToCatDtoList(convertSendAndReceive(request, CatMethod.FILTER_BY_OWNER));
    }

    private Object convertSendAndReceive(CatRelatedRequest request, CatMethod method) {
        Object result = template.convertSendAndReceive(
                CatRabbitNames.MAIN_QUEUE_ROUTING_KEY,
                request,
                m -> {
                    m.getMessageProperties().getHeaders().put(CatRabbitNames.METHOD_NAMES_HEADER, method);
                    return m;
                }
        );
        return result;
    }

    private List<CatDto> tryConvertToCatDtoList(Object originalResult) {
        if (!(originalResult instanceof List<?> listResult)) {
            throw RemoteServiceException.InvalidResponseFormat();
        }

        if (!listResult.isEmpty() && !(listResult.get(0) instanceof CatDto)) {
            throw RemoteServiceException.InvalidResponseFormat();
        }

        return (List<CatDto>) listResult;
    }

    private CatDto extractFirstDtoFromResult(Object originalResult) {
        return tryConvertToCatDtoList(originalResult).get(0);
    }
}
