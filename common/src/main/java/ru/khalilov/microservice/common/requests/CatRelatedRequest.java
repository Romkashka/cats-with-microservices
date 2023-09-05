package ru.khalilov.microservice.common.requests;

import lombok.Builder;
import ru.khalilov.microservice.common.models.Color;

import java.time.LocalDate;

@Builder
public record CatRelatedRequest(Long CatId, String Name, String Breed, Color Color, LocalDate Birthday, Long OwnerId, Long AdditionalCatId) {
}
