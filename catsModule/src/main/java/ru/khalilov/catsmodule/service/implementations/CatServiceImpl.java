package ru.khalilov.catsmodule.service.implementations;

import lombok.NonNull;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.khalilov.catsmodule.dao.CatRepository;
import ru.khalilov.catsmodule.dao.entities.Cat;
import ru.khalilov.microservice.common.models.Color;
import ru.khalilov.microservice.common.dtos.CatDto;
import ru.khalilov.microservice.common.exceptions.ServiceException;
import ru.khalilov.catsmodule.service.mappers.CatMapper;
import ru.khalilov.catsmodule.service.CatService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CatServiceImpl implements CatService {
    private final CatRepository catRepository;

    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public CatDto create(@NonNull String name, @NonNull LocalDate birthDay, @NonNull String breed, @NonNull Color color, Long ownerId) {
        Cat cat = new Cat(name, birthDay, breed, color, null);
        cat = catRepository.save(cat);
        return CatMapper.asDto(cat);
    }

    @RabbitHandler
    @Payload(expression = "")
    @Override
    public CatDto read(long id) {
        return CatMapper.asDto(getCat(id));
    }

    @Override
    public CatDto update(long id, String name, LocalDate birthDay, String breed, Color color) {
        Cat cat = getCat(id);

        if (name == null && birthDay == null && breed == null && color == null) {
            throw ServiceException.nothingToUpdate();
        }

        if (name != null) cat.setName(name);
        if (birthDay != null) cat.setBirthDay(birthDay);
        if (breed != null) cat.setBreed(breed);
        if (color != null) cat.setColor(color);

        return CatMapper.asDto(catRepository.save(cat));

    }

    @Override
    public CatDto setOwner(long catId, Long ownerId) {
        Cat cat = getCat(catId);
        cat.setOwnerId(ownerId);
        return CatMapper.asDto(catRepository.save(cat));
    }

    @Override
    public void deleteById(long id) {
        catRepository.deleteById(id);
    }

    @Override
    public void makeFriends(long catAId, long catBId) {
        Cat catA = getCat(catAId);
        Cat catB = getCat(catBId);

        catA.addFriend(catB);
        catRepository.save(catA);
    }

    @Override
    public void unmakeFriends(long catAId, long catBId) {
        Cat catA = getCat(catAId);
        Cat catB = getCat(catBId);

        catA.removeFriend(catB);
        catB.removeFriend(catA);

        catRepository.save(catA);
    }

    @Override
    public @NonNull List<CatDto> findAll() {
        List<Cat> result = catRepository.findAll();
        return convertCatsToCatDtos(result);
    }

    @Override
    public @NonNull List<CatDto> filterByColor(@NonNull Color color) {
        List<Cat> result = catRepository.findCatsByColor(color);
        return convertCatsToCatDtos(result);
    }

    @Override
    public @NonNull List<CatDto> filterByColorAndOwner(@NonNull Color color, Long ownerId) {
        return convertCatsToCatDtos(catRepository.findCatsByColorAndOwnerId(color, ownerId));
    }

    @Override
    public @NonNull List<CatDto> filterByBreed(@NonNull String breed) {
        List<Cat> result = catRepository.findCatsByBreed(breed);
        return convertCatsToCatDtos(result);
    }

    @Override
    public @NonNull List<CatDto> filterByBreedAndOwner(@NonNull String breed, Long ownerId) {
        return convertCatsToCatDtos(catRepository.findCatsByBreedAndOwnerId(breed, ownerId));
    }

    @Override
    public @NonNull List<CatDto> filterByName(@NonNull String name) {
        List<Cat> result = catRepository.findCatsByName(name);
        return convertCatsToCatDtos(result);
    }

    @Override
    public @NonNull List<CatDto> filterByNameAndOwner(@NonNull String name, Long ownerId) {
        return convertCatsToCatDtos(catRepository.findCatsByNameAndOwnerId(name, ownerId));
    }

    @Override
    public @NonNull List<CatDto> filterByOwner(Long ownerId) {
        List<Cat> result = catRepository.findCatsByOwnerId(ownerId);
        return convertCatsToCatDtos(result);
    }

    @Override
    public @NonNull List<CatDto> filterByBirthDay(@NonNull LocalDate date) {
        List<Cat> result = catRepository.findCatsByBirthDay(date);
        return convertCatsToCatDtos(result);
    }

    @Override
    public @NonNull List<CatDto> filterByBirthDayAndOwner(@NonNull LocalDate date, Long ownerId) {
        return convertCatsToCatDtos(catRepository.findCatsByBirthDayAndOwnerId(date, ownerId));
    }

    private List<CatDto> convertCatsToCatDtos(List<Cat> cats) {
        return cats.stream().collect(ArrayList::new, (list, item) -> list.add(CatMapper.asDto(item)), ArrayList::addAll);
    }

    private Cat getCat(long id) {
        Cat cat = catRepository.findById(id);
        if (cat == null) {
            throw ServiceException.noEntityWithGivenId(Cat.class.getTypeName(), id);
        }

        return cat;
    }
}
