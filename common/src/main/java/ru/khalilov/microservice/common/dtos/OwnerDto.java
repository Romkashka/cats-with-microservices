package ru.khalilov.microservice.common.dtos;

import lombok.NonNull;
import ru.khalilov.microservice.common.models.UserRoles;

import java.util.List;

public record OwnerDto(long Id, @NonNull String Name, @NonNull String Surname, @NonNull String Username, @NonNull String Password, List<Long> CatIds, @NonNull UserRoles Role) {
}
