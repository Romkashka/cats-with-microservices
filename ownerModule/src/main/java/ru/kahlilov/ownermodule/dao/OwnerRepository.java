package ru.kahlilov.ownermodule.dao;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.kahlilov.ownermodule.dao.entities.Owner;

import java.util.List;

@Component
@Repository("OwnerRepository")
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findById(long id);

    List<Owner> findOwnersByIdIn(List<Long> ids);

    List<Owner> findByUsername(@NonNull String username);

    boolean existsByNameAndSurname(String name, String surname);
}
