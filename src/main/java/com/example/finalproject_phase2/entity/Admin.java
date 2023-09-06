package com.example.finalproject_phase2.entity;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Admin extends Person  {
    @Builder

    public Admin(String firstName, String lastName, String nationalId, String email, String password, LocalDate registerDate, LocalTime registerTime, Boolean isEnable) {
        super(firstName, lastName, nationalId, email, password, registerDate, registerTime, isEnable);
    }
}
