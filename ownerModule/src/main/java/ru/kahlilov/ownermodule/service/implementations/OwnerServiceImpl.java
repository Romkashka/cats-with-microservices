package ru.kahlilov.ownermodule.service.implementations;

import lombok.NonNull;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kahlilov.ownermodule.dao.OwnerRepository;
import ru.kahlilov.ownermodule.rabbit.InterServerRequestSender;
import ru.kahlilov.ownermodule.service.OwnerService;
import ru.kahlilov.ownermodule.service.mappers.OwnerMapper;
import ru.khalilov.microservice.common.dtos.CatDto;
import ru.khalilov.microservice.common.dtos.OwnerDto;
import ru.kahlilov.ownermodule.dao.entities.Owner;
import ru.khalilov.microservice.common.exceptions.CredentialsException;
import ru.khalilov.microservice.common.exceptions.ServiceException;
import ru.khalilov.microservice.common.models.OwnerDetails;
import ru.khalilov.microservice.common.models.UserRoles;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    private InterServerRequestSender interServerRequestSender;

    public OwnerServiceImpl(OwnerRepository ownerRepository, InterServerRequestSender interServerRequestSender) {
        this.ownerRepository = ownerRepository;
        this.interServerRequestSender = interServerRequestSender;
    }

    @Override
    public OwnerDto create(@NonNull String name, @NonNull String surname, @NonNull String username, @NonNull String password) {
        if (!ownerRepository.findByUsername(username).isEmpty()) {
            throw CredentialsException.usernameAlreadyExists();
        }

        Owner owner = new Owner(name, surname, username, password, UserRoles.USER);
        return OwnerMapper.asDto(ownerRepository.save(owner));
    }

    @Override
    public OwnerDto read(long id) {
        return OwnerMapper.asDto(getOwner(id));
    }

    @Override
    public OwnerDto update(long id, String name, String surname) {
        if (name == null && surname == null) {
            throw ServiceException.nothingToUpdate();
        }
        Owner owner = getOwner(id);

        if (name != null) owner.setName(name);
        if (surname != null) owner.setSurname(surname);

        return OwnerMapper.asDto(ownerRepository.save(owner));
    }

    @Override
    public void deleteById(long id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public OwnerDto addCat(long ownerId, long catId) {
        interServerRequestSender.setOwner(catId, ownerId);
        return OwnerMapper.asDto(getOwner(ownerId));
    }

    @Override
    public OwnerDto removeCat(long ownerId, long catId) {
        interServerRequestSender.setOwner(catId, null);
        return OwnerMapper.asDto(getOwner(ownerId));
    }

    @Override
    public @NonNull List<OwnerDto> findAll() {
        List<Owner> result = ownerRepository.findAll();
        return result.stream().collect(ArrayList::new, (list, item) -> list.add(OwnerMapper.asDto(item)), ArrayList::addAll);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Owner> owners = ownerRepository.findByUsername(username);

        if (owners.isEmpty()) {
            throw new UsernameNotFoundException("No owners with username '" + username + "' was found!");
        }
        if (owners.size() > 1) {
            throw new AuthenticationServiceException("There are more than 1 owner with username '" + username + "'...");
        }

        return new OwnerDetails(OwnerMapper.asDto(owners.get(0)));
    }

    private CatDto getCat(long id) {
        CatDto catDto = interServerRequestSender.read(id);
        if (catDto == null) {
            throw ServiceException.noEntityWithGivenId(CatDto.class.getTypeName(), id);
        }

        return catDto;
    }

    private Owner getOwner(long id) {
        Owner owner = ownerRepository.findById(id);
        if (owner == null) {
            throw ServiceException.noEntityWithGivenId(Owner.class.getTypeName(), id);
        }
        List<CatDto> cats = interServerRequestSender.filterByOwner(owner.getId());
        owner.setCatIds(cats.stream().collect(ArrayList::new, (list, item) -> list.add(item.Id()), ArrayList::addAll));

        return owner;
    }
}
