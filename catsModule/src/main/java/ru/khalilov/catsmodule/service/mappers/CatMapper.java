package ru.khalilov.catsmodule.service.mappers;

import lombok.NonNull;
import ru.khalilov.catsmodule.dao.entities.Cat;
import ru.khalilov.microservice.common.dtos.CatDto;

import java.util.ArrayList;

public class CatMapper {
    public static CatDto asDto(@NonNull Cat cat) {
        Long ownerId = cat.getOwnerId();
        return new CatDto(cat.getId(), cat.getName(), cat.getBirthDay(), cat.getBreed(), cat.getColor(), ownerId, cat.getFriends().stream().collect(
                ArrayList::new,
                (list, item) -> list.add(item.getId()),
                ArrayList::addAll)
        );
    }
}
