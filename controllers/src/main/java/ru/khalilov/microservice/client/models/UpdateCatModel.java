package ru.khalilov.microservice.client.models;

import jakarta.validation.constraints.Min;
import lombok.NonNull;
import ru.khalilov.microservice.client.util.Validation;
import ru.khalilov.microservice.common.models.Color;

import java.time.LocalDate;

public record UpdateCatModel(@Min(0) Long Id,
                             @NonNull String Name,
                             @NonNull LocalDate Birthday,
                             @NonNull String Breed,
                             @NonNull Color Color
) {

    public UpdateCatModel(@Min(0) Long Id, @NonNull String Name, @NonNull LocalDate Birthday, @NonNull String Breed, @NonNull Color Color) {
        this.Id = Id;
        this.Name = Name;
        this.Birthday = Validation.validateBirthday(Birthday);
        this.Breed = Breed;
        this.Color = Color;
    }
}
