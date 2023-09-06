package com.example.finalproject_phase2.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer extends Person {
    @OneToOne
    Wallet wallet;
@Builder

    public Customer(String firstName, String lastName, String nationalId, String email, String password, LocalDate registerDate, LocalTime registerTime, Boolean isEnable, Wallet wallet) {
        super(firstName, lastName, nationalId, email, password, registerDate, registerTime, isEnable);
        this.wallet = wallet;
    }
}
