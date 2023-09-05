package ru.kahlilov.ownermodule.dao.entities;

import jakarta.persistence.*;
import lombok.*;
import ru.khalilov.microservice.common.models.UserRoles;
import ru.khalilov.microservice.common.util.Validation;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owner")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class Owner {
    @Id @GeneratedValue
    @NonNull
    @Column(name = "owner_id")
    private long id;

    @Transient
    private List<Long> catIds;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    public Owner(@NonNull String name, @NonNull String surname, @NonNull String username, @NonNull String password, @NonNull UserRoles role) {
        setName(name);
        setSurname(surname);
        setUsername(username);
        setPassword(password);
        setRole(role);
        catIds = new ArrayList<>();
    }


    public void setName(String name) {
        this.name = Validation.validateOwnerName(name);
    }

    public void setSurname(String surname) {
        this.surname = Validation.validateOwnerName(surname);
    }
}
