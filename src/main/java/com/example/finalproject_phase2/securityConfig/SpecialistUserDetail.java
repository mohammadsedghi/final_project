package com.example.finalproject_phase2.securityConfig;

import com.example.finalproject_phase2.entity.Specialist;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SpecialistUserDetail implements UserDetails {
    private final Specialist specialist;

    public SpecialistUserDetail(Specialist specialist) {
        this.specialist = specialist;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("SPECIALIST"));
    }

    @Override
    public String getPassword() {
        return specialist.getPassword();
    }

    @Override
    public String getUsername() {
        return specialist.getEmail();
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
