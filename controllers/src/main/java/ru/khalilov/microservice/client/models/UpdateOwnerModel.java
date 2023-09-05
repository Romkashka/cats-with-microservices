package ru.khalilov.microservice.client.models;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateOwnerModel {
    @Min(0)
    private long id;
    private String name;
    private String surname;
}
