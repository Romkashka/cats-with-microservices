package ru.khalilov.catsmodule.dao.entities;

import jakarta.persistence.*;
import lombok.*;
import ru.khalilov.microservice.common.models.Color;
import ru.khalilov.microservice.common.util.Validation;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="cat")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter @Setter
public class Cat {
    @NonNull @Id @GeneratedValue
    @Column(name = "cat_id")
    private long id;
    @NonNull
    private String name;
    @NonNull
    private LocalDate birthDay;
    @NonNull
    private String breed;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Color color;


    private Long ownerId;

    @NonNull
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Cat.class)
    private Set<Cat> friendOf;

    @NonNull
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Cat.class)
    private Set<Cat> friends;

    public Cat(@NonNull String name, @NonNull LocalDate birthDay, @NonNull String breed, @NonNull Color color, Long ownerId) {
        setName(name);
        setBirthDay(birthDay);
        setBreed(breed);
        setColor(color);
        setOwnerId(ownerId);
        setFriends(new HashSet<>());
        setFriendOf(new HashSet<>());
    }

    public void addFriend(Cat friend) {
        if (friends.contains(friend)) {
            return;
        }

        friends.add(friend);
        friendOf.add(friend);
        friend.addFriend(this);
    }

    public void removeFriend(Cat friend) {
        if (!friends.contains(friend)) {
            return;
        }

        friends.remove(friend);
        friendOf.remove(friend);
        friend.removeFriend(this);
    }

    public void setName(@NonNull String name) {
        this.name = Validation.validateCatName(name);
    }

    public void setBreed(@NonNull String breed) {
        this.breed = Validation.validateBreed(breed);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDay=" + birthDay +
                ", breed='" + breed + '\'' +
                ", color=" + color +
                ", ownerId=" + ownerId +
                '}';
    }

    private void addFriendOf(Cat friend) {
        if (friendOf.contains(friend)) {
            return;
        }
        friendOf.add(friend);
    }
}
