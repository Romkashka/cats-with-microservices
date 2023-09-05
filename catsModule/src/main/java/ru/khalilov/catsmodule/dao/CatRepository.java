package ru.khalilov.catsmodule.dao;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khalilov.catsmodule.dao.entities.Cat;
import ru.khalilov.microservice.common.models.Color;

import java.time.LocalDate;
import java.util.List;

@Repository("CatRepository")
public interface CatRepository extends JpaRepository<Cat, Long> {
    Cat findById(long id);

    List<Cat> findCatsByIdIn(List<Long> ids);

    List<Cat> findCatsByBirthDay(LocalDate birthday);

    List<Cat>findCatsByBirthDayAndOwnerId(@NonNull LocalDate birthday, Long ownerId);

    List<Cat> findCatsByColor(Color color);

    List<Cat> findCatsByColorAndOwnerId(@NonNull Color color, Long ownerId);

    List<Cat> findCatsByBreed(String breed);

    List<Cat> findCatsByBreedAndOwnerId(@NonNull String breed, Long ownerId);

    List<Cat> findCatsByName(String name);

    List<Cat> findCatsByNameAndOwnerId(@NonNull String name, Long ownerId);

    List<Cat> findCatsByOwnerId(Long ownerId);

    boolean existsByName(String name);
}
