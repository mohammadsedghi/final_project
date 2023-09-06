package com.example.finalproject_phase2.entity;

import com.example.finalproject_phase2.base.BaseEntity;
import com.example.finalproject_phase2.entity.enumeration.SpecialistSelectionOfOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpecialistSuggestion extends BaseEntity<Long> {
    @ManyToOne
    Specialist specialist;
    @ManyToOne
    Orders order;
    @NotNull(message = "DateOfSuggestion must be have value")
    LocalDate DateOfSuggestion;
    @NotNull(message = "TimeOfSuggestion must be have value")
    LocalTime TimeOfSuggestion;
    @NotNull(message = "proposedPrice must be have value")
     @Positive(message = "price must be positive")
    Double proposedPrice;
    @NotNull(message = "TimeOfStartWork must be have value")
    LocalTime TimeOfStartWork;
    @NotNull(message = "DateOfStartWork must be have value")
    LocalDate DateOfStartWork;
    @NotNull(message = "durationOfWorkPerHour must be have value")
    Integer durationOfWorkPerHour;
    @Enumerated(EnumType.STRING)
    SpecialistSelectionOfOrder specialistSelectionOfOrder;

}
