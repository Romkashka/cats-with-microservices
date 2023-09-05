package ru.kahlilov.ownermodule.service.mappers;

import lombok.NonNull;
import ru.kahlilov.ownermodule.dao.entities.Owner;
import ru.khalilov.microservice.common.dtos.OwnerDto;

import java.util.ArrayList;

public class OwnerMapper {
    public static OwnerDto asDto(@NonNull Owner owner) {
        return new OwnerDto(owner.getId(), owner.getName(), owner.getSurname(), owner.getUsername(), owner.getPassword(), owner.getCatIds(), owner.getRole());
    }
}
