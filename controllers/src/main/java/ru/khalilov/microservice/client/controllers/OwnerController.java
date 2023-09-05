package ru.khalilov.microservice.client.controllers;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.khalilov.microservice.client.models.CreateOwnerModel;
import ru.khalilov.microservice.client.models.UpdateOwnerModel;
import ru.khalilov.microservice.common.models.OwnerDetails;
import ru.khalilov.microservice.client.services.RemoteOwnerService;
import ru.khalilov.microservice.common.dtos.OwnerDto;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
@Validated
public class OwnerController {
    private final RemoteOwnerService ownerService;
    private final PasswordEncoder passwordEncoder;

    public OwnerController(RemoteOwnerService ownerService, PasswordEncoder passwordEncoder) {
        this.ownerService = ownerService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/you")
    public OwnerDto getOwner(Authentication authentication) {
        return ownerService.read(getOwnerFromAuthentication(authentication).Id());
    }

    @PostMapping("/create")
    public OwnerDto createOwner(@NotNull @RequestBody CreateOwnerModel model) {
        return ownerService.create(model.Name(), model.Surname(), model.Username(), passwordEncoder.encode(model.Password()));
    }

    @PutMapping("/update")
    public OwnerDto updateOwner(@NotNull UpdateOwnerModel model, Authentication authentication) {
        return ownerService.update(getOwnerFromAuthentication(authentication).Id(), model.getName(), model.getSurname());
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(@Min(0) long id) {
        ownerService.deleteById(id);
    }

    @PutMapping("/addCat")
    public OwnerDto addCat(Authentication authentication, @Min(0) long catId) {
        return ownerService.addCat(getOwnerFromAuthentication(authentication).Id(), catId);
    }

    @PutMapping("/removeCat")
    public OwnerDto removeCat(Authentication authentication, @Min(0) long catId) {
        return ownerService.removeCat(getOwnerFromAuthentication(authentication).Id(), catId);
    }

    @GetMapping
    public List<OwnerDto> getAllOwners() {
        return ownerService.findAll();
    }

    private OwnerDto getOwnerFromAuthentication(Authentication authentication) {
        return ((OwnerDetails) authentication.getPrincipal()).getOwnerDto();
    }
}
