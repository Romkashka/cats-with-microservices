package ru.khalilov.microservice.common.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.khalilov.microservice.common.dtos.OwnerDto;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class OwnerDetails implements UserDetails {
    @Getter
    private OwnerDto ownerDto;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + ownerDto.Role()));
    }

    @Override
    public String getPassword() {
        return ownerDto.Password();
    }

    @Override
    public String getUsername() {
        return ownerDto.Username();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
