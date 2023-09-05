package ru.khalilov.catsmodule.rabbit;

import ru.khalilov.catsmodule.service.implementations.CatServiceImpl;
import ru.khalilov.microservice.common.constants.CatRabbitNames;
import ru.khalilov.microservice.common.methodEnums.CatMethod;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.khalilov.microservice.common.requests.CatRelatedRequest;
import ru.khalilov.catsmodule.service.CatService;
import ru.khalilov.microservice.common.dtos.CatDto;

import java.util.List;

@Component
@RabbitListener(queues = CatRabbitNames.MAIN_QUEUE_NAME)
public class CatReceiver {
    private final CatService catService;

    public CatReceiver(CatService catService) {
        this.catService = catService;
    }

    @RabbitHandler
    public List<CatDto> receive(@Header(CatRabbitNames.METHOD_NAMES_HEADER) CatMethod method, @Payload CatRelatedRequest request) {
        switch (method) {
            case GET_CAT -> {
                return List.of(catService.read(request.CatId()));
            }
            case GET_ALL_CATS -> {
                return catService.findAll();
            }
            case CREATE -> {
                return List.of(catService.create(request.Name(), request.Birthday(), request.Breed(), request.Color(), request.OwnerId()));
            }
            case DELETE -> {
                catService.deleteById(request.CatId());
                return List.of();
            }
            case UPDATE -> {
                return List.of(catService.update(request.CatId(), request.Name(), request.Birthday(), request.Breed(), request.Color()));
            }
            case SET_OWNER -> {
                return List.of(catService.setOwner(request.CatId(), request.OwnerId()));
            }
            case MAKE_FRIENDS -> {
                catService.makeFriends(request.CatId(), request.AdditionalCatId());
                return List.of();
            }
            case UNMAKE_FRIENDS -> {
                catService.unmakeFriends(request.CatId(), request.AdditionalCatId());
                return List.of();
            }
            case FILTER_BY_NAME -> {
                return catService.filterByName(request.Name());
            }
            case FILTER_BY_NAME_AND_OWNER -> {
                return catService.filterByNameAndOwner(request.Name(), request.OwnerId());
            }
            case FILTER_BY_BREED -> {
                return catService.filterByBreed(request.Breed());
            }
            case FILTER_BY_BREED_AND_OWNER -> {
                return catService.filterByBreedAndOwner(request.Breed(), request.OwnerId());
            }
            case FILTER_BY_COLOR -> {
                return catService.filterByColor(request.Color());
            }
            case FILTER_BY_COLOR_AND_OWNER -> {
                return catService.filterByColorAndOwner(request.Color(), request.OwnerId());
            }
            case FILTER_BY_OWNER -> {
                return catService.filterByOwner(request.OwnerId());
            }
            case FILTER_BY_BIRTHDAY -> {
                return catService.filterByBirthDay(request.Birthday());
            }
            case FILTER_BY_BIRTHDAY_AND_OWNER -> {
                return catService.filterByBirthDayAndOwner(request.Birthday(), request.OwnerId());
            }
            default -> {
                throw new IllegalArgumentException("No method connected to '" + method.toString() + "'");
            }
        }
    }
}
