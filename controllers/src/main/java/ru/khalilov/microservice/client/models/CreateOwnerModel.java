package ru.khalilov.microservice.client.models;

import lombok.NonNull;

public record CreateOwnerModel(@NonNull String Name, @NonNull String Surname, @NonNull String Username, @NonNull String Password) {
}
