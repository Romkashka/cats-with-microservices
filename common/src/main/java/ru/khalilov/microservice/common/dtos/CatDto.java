package ru.khalilov.microservice.common.dtos;

import lombok.NonNull;
import ru.khalilov.microservice.common.models.Color;

import java.time.LocalDate;
import java.util.List;

public record CatDto(long Id, @NonNull String Name, @NonNull LocalDate Birthday, @NonNull String Breed, @NonNull Color Color, Long OwnerId, List<Long> Friends) {
}
