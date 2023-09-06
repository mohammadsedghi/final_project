package com.example.finalproject_phase2.dto.personDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonResult {

    String firstName;

    String lastName;

    String nationalId;
    String email;
    LocalDate registerDate;
   LocalTime registerTime;
}
