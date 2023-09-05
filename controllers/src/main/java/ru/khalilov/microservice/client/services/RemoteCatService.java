package ru.khalilov.microservice.client.services;

import lombok.NonNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.khalilov.microservice.common.exceptions.RemoteServiceException;
import ru.khalilov.microservice.common.constants.CatRabbitNames;
import ru.khalilov.microservice.common.dtos.CatDto;
import ru.khalilov.microservice.common.methodEnums.CatMethod;
import ru.khalilov.microservice.common.models.Color;
import ru.khalilov.microservice.common.requests.CatRelatedRequest;

import java.time.LocalDate;
import java.util.List;

@Service
public class RemoteCatService {
    public RemoteCatService(RabbitTemplate template) {
        this.template = template;
    }

    private final RabbitTemplate template;

    public CatDto create(@NonNull String name, @NonNull LocalDate birthDay, @NonNull String breed, @NonNull Color color, Long ownerId) {
        CatRelatedRequest request = CatRelatedRequest
                .builder()
                .Name(name)
                .Birthday(birthDay)
                .Breed(breed)
                .Color(color)
                .OwnerId(ownerId)
                .build();
        return extractFirstDtoFromResult(convertSendAndReceive(request, CatMethod.CREATE));
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

    public void deleteById(long id) {
        CatRelatedRequest request = CatRelatedRequest.builder().CatId(id).build();
        Object result = convertSendAndReceive(request, CatMethod.DELETE);

        tryConvertToCatDtoList(result);
    }

    public void makeFriends(long catAId, long catBId) {
        CatRelatedRequest request = CatRelatedRequest.builder().CatId(catAId).AdditionalCatId(catBId).build();
        Object result = convertSendAndReceive(request, CatMethod.MAKE_FRIENDS);

        tryConvertToCatDtoList(result);
    }

    public void unmakeFriends(long catAId, long catBId) {
        CatRelatedRequest request = CatRelatedRequest.builder().CatId(catAId).AdditionalCatId(catBId).build();
        Object result = convertSendAndReceive(request, CatMethod.UNMAKE_FRIENDS);

        tryConvertToCatDtoList(result);
    }

    public @NonNull List<CatDto> findAll() {
        return tryConvertToCatDtoList(convertSendAndReceive(CatRelatedRequest.builder().build(), CatMethod.GET_ALL_CATS));
    }

    public @NonNull List<CatDto> filterByColor(@NonNull Color color) {
        CatRelatedRequest request = CatRelatedRequest.builder().Color(color).build();
        return tryConvertToCatDtoList(convertSendAndReceive(request, CatMethod.FILTER_BY_COLOR));
    }

    public @NonNull List<CatDto> filterByColorAndOwner(@NonNull Color color, Long ownerId) {

        CatRelatedRequest request = CatRelatedRequest.builder().Color(color).OwnerId(ownerId).build();
        return tryConvertToCatDtoList(convertSendAndReceive(request, CatMethod.FILTER_BY_COLOR_AND_OWNER));
    }

    public @NonNull List<CatDto> filterByBreed(@NonNull String breed) {
        CatRelatedRequest request = CatRelatedRequest.builder().Breed(breed).build();
        return tryConvertToCatDtoList(convertSendAndReceive(request, CatMethod.FILTER_BY_BREED));
    }

    public @NonNull List<CatDto> filterByBreedAndOwner(@NonNull String breed, Long ownerId) {
        CatRelatedRequest request = CatRelatedRequest.builder().Breed(breed).OwnerId(ownerId).build();
        return tryConvertToCatDtoList(convertSendAndReceive(request, CatMethod.FILTER_BY_BREED_AND_OWNER));
    }

    public @NonNull List<CatDto> filterByName(@NonNull String name) {
        CatRelatedRequest request = CatRelatedRequest.builder().Name(name).build();
        return tryConvertToCatDtoList(convertSendAndReceive(request, CatMethod.FILTER_BY_NAME));
    }

    public @NonNull List<CatDto> filterByNameAndOwner(@NonNull String name, Long ownerId) {
        CatRelatedRequest request = CatRelatedRequest.builder().Name(name).OwnerId(ownerId).build();
        return tryConvertToCatDtoList(convertSendAndReceive(request, CatMethod.FILTER_BY_NAME_AND_OWNER));
    }

    public @NonNull List<CatDto> filterByOwner(Long ownerId) {
        CatRelatedRequest request = CatRelatedRequest.builder().OwnerId(ownerId).build();
        return tryConvertToCatDtoList(convertSendAndReceive(request, CatMethod.FILTER_BY_OWNER));
    }

    public @NonNull List<CatDto> filterByBirthDay(@NonNull LocalDate date) {
        CatRelatedRequest request = CatRelatedRequest.builder().Birthday(date).build();
        return tryConvertToCatDtoList(convertSendAndReceive(request, CatMethod.FILTER_BY_BIRTHDAY));
    }

    public @NonNull List<CatDto> filterByBirthDayAndOwner(@NonNull LocalDate date, Long ownerId) {
        CatRelatedRequest request = CatRelatedRequest.builder().Birthday(date).OwnerId(ownerId).build();
        return tryConvertToCatDtoList(convertSendAndReceive(request, CatMethod.FILTER_BY_BIRTHDAY_AND_OWNER));
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
