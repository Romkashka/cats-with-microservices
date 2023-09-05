package ru.kahlilov.ownermodule.service;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.khalilov.microservice.common.dtos.OwnerDto;

import java.util.List;

@Service
public interface OwnerService extends UserDetailsService {
    OwnerDto create(@NonNull String name, @NonNull String surname, @NonNull String username, @NonNull String password);

    OwnerDto read(long id);

    OwnerDto update(long id, String name, String surname);

    void deleteById(long id);

    OwnerDto addCat(long ownerId, long catId);

    OwnerDto removeCat(long ownerId, long catId);

    @NonNull List<OwnerDto> findAll();
}
