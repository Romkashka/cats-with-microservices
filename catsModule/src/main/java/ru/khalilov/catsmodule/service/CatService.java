package ru.khalilov.catsmodule.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.khalilov.microservice.common.models.Color;
import ru.khalilov.microservice.common.dtos.CatDto;

import java.time.LocalDate;
import java.util.List;

@Service
public interface CatService {
    CatDto create(@NonNull String name, @NonNull LocalDate birthDay, @NonNull String breed, @NonNull Color color, Long ownerId);

    CatDto read(long id);

    CatDto update(long id, String name, LocalDate birthDay, String breed, Color color);
    CatDto setOwner(long catId, Long ownerId);

    void deleteById(long id);

    void makeFriends(long catAId, long catBId);

    void unmakeFriends(long catAId, long catBId);

    @NonNull List<CatDto> findAll();

    @NonNull List<CatDto> filterByColor(@NonNull Color color);

    @NonNull List<CatDto> filterByColorAndOwner(@NonNull Color color, Long ownerId);

    @NonNull List<CatDto> filterByBreed(@NonNull String breed);

    @NonNull List<CatDto> filterByBreedAndOwner(@NonNull String breed, Long ownerId);

    @NonNull List<CatDto> filterByName(@NonNull String name);

    @NonNull List<CatDto> filterByNameAndOwner(@NonNull String name, Long ownerId);

    @NonNull List<CatDto> filterByOwner(Long ownerId);

    @NonNull List<CatDto> filterByBirthDay(@NonNull LocalDate date);

    @NonNull List<CatDto> filterByBirthDayAndOwner(@NonNull LocalDate date, Long ownerId);
}
