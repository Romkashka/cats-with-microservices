package ru.khalilov.microservice.client.models;

import lombok.NonNull;
import ru.khalilov.microservice.client.util.Validation;
import ru.khalilov.microservice.common.models.Color;

import java.time.LocalDate;

public record CreateCatModel(
        @NonNull String Name,
        @NonNull LocalDate Birthday,
        @NonNull String Breed,
        @NonNull Color Color
) {
    public CreateCatModel(@NonNull String Name, @NonNull LocalDate Birthday, @NonNull String Breed, @NonNull Color Color) {
        this.Name = Name;
        this.Birthday = Validation.validateBirthday(Birthday);
        this.Breed = Breed;
        this.Color = Color;
    }
}
