package ru.khalilov.microservice.client.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.khalilov.microservice.client.models.CreateCatModel;
import ru.khalilov.microservice.client.models.UpdateCatModel;
import ru.khalilov.microservice.common.models.OwnerDetails;
import ru.khalilov.microservice.client.services.RemoteCatService;
import ru.khalilov.microservice.client.util.Validation;
import ru.khalilov.microservice.common.dtos.CatDto;
import ru.khalilov.microservice.common.dtos.OwnerDto;
import ru.khalilov.microservice.common.models.Color;
import ru.khalilov.microservice.common.models.UserRoles;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/cats")
@Validated
public class CatController {
    private final RemoteCatService catService;

    public CatController(RemoteCatService catService) {
        this.catService = catService;
    }

    @GetMapping("/getAllCats")
    public List<CatDto> getAllCats(Authentication authentication) {
        OwnerDto owner = getOwnerDtoFromAuthentication(authentication);
        System.out.println(owner.Id());
        if (owner.Role().equals(UserRoles.USER)) {
            return catService.filterByOwner(owner.Id());
        }
        return catService.findAll();
    }

    @GetMapping("/{id}")
    public CatDto getCat(@PathVariable("id") @Min(0) long id, Authentication authentication) {
        OwnerDto owner = getOwnerDtoFromAuthentication(authentication);
        CatDto catDto = catService.read(id);
        if (!catDto.OwnerId().equals(owner.Id())) {
            throw new AccessDeniedException("You are not an owner of this cat");
        }
        return catService.read(id);
    }

    @PostMapping("/create")
    public CatDto createCat(@RequestBody @NonNull CreateCatModel createCatModel, Authentication authentication) {
        OwnerDto owner = ((OwnerDetails) authentication.getPrincipal()).getOwnerDto();
        return catService.create(createCatModel.Name(), createCatModel.Birthday(), createCatModel.Breed(), createCatModel.Color(), owner.Id());
    }

    @PutMapping("/update")
    public CatDto updateCat(@Valid @RequestBody @NonNull UpdateCatModel catModel, Authentication authentication) {
        OwnerDto owner = getOwnerDtoFromAuthentication(authentication);
        CatDto catDto = catService.read(catModel.Id());
        if (!catDto.OwnerId().equals(owner.Id())) {
            throw new AccessDeniedException("You are not an owner of this cat");
        }
        return catService.update(catModel.Id(), catModel.Name(), catModel.Birthday(), catModel.Breed(), catModel.Color());
    }

    @DeleteMapping("/delete")
    public void deleteCat(@Min(0) long id, Authentication authentication) {
        OwnerDto owner = getOwnerDtoFromAuthentication(authentication);
        CatDto catDto = catService.read(id);
        if (!catDto.OwnerId().equals(owner.Id())) {
            throw new AccessDeniedException("You are not an owner of this cat");
        }
        catService.deleteById(id);
    }

    @PutMapping("/makeFriends")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void makeFriends(@Min(0) long id1, @Min(0) long id2) {
        catService.makeFriends(id1, id2);
    }

    @PutMapping("/unmakeFriends")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void unmakeFriends(@Min(0) long id1, @Min(0) long id2) {
        catService.unmakeFriends(id1, id2);
    }

    @GetMapping("/getBy/Color")
    public List<CatDto> getByColor(@NotNull Color color, Authentication authentication) {
        OwnerDto owner = getOwnerDtoFromAuthentication(authentication);
        if (owner.Role().equals(UserRoles.USER)) {
            return catService.filterByColorAndOwner(color, owner.Id());
        }
        return catService.filterByColor(color);
    }

    @GetMapping("/getBy/breed")
    public List<CatDto> getByBreed(@NonNull String breed, Authentication authentication) {
        OwnerDto owner = getOwnerDtoFromAuthentication(authentication);
        if (owner.Role().equals(UserRoles.USER)) {
            return catService.filterByBreedAndOwner(breed, owner.Id());
        }
        return catService.filterByBreed(breed);
    }

    @GetMapping("getBy/Name")
    public List<CatDto> getByName(@NonNull String name, Authentication authentication) {
        OwnerDto owner = getOwnerDtoFromAuthentication(authentication);
        if (owner.Role().equals(UserRoles.USER)) {
            return catService.filterByNameAndOwner(name, owner.Id());
        }
        return catService.filterByName(name);
    }

    @GetMapping("getBy/Owner")
    public List<CatDto> getByOwnerId(@Min(0) long ownerId) {
        return catService.filterByOwner(ownerId);
    }

    @GetMapping("getBy/birthday")
    public List<CatDto> getByBirthday(LocalDate birthday, Authentication authentication) {
        OwnerDto owner = getOwnerDtoFromAuthentication(authentication);
        if (owner.Role().equals(UserRoles.USER)) {
            return catService.filterByBirthDayAndOwner(birthday, owner.Id());
        }
        return catService.filterByBirthDay(Validation.validateBirthday(birthday));
    }

    private OwnerDto getOwnerDtoFromAuthentication(Authentication authentication) {
        return ((OwnerDetails) authentication.getPrincipal()).getOwnerDto();
    }
}
