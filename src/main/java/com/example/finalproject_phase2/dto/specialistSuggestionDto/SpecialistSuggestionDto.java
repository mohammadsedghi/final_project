package com.example.finalproject_phase2.dto.specialistSuggestionDto;

import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.enumeration.SpecialistSelectionOfOrder;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpecialistSuggestionDto {
    Specialist specialist;
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
    SpecialistSelectionOfOrder specialistSelectionOfOrder;

    @Builder
    public SpecialistSuggestionDto(Specialist specialist, Orders order, LocalDate dateOfSuggestion, LocalTime timeOfSuggestion, Double proposedPrice, LocalTime timeOfStartWork, LocalDate dateOfStartWork, Integer durationOfWorkPerHour, SpecialistSelectionOfOrder specialistSelectionOfOrder) {
        this.specialist = specialist;
        this.order = order;
        DateOfSuggestion = dateOfSuggestion;
        TimeOfSuggestion = timeOfSuggestion;
        this.proposedPrice = proposedPrice;
        TimeOfStartWork = timeOfStartWork;
        DateOfStartWork = dateOfStartWork;
        this.durationOfWorkPerHour = durationOfWorkPerHour;
        this.specialistSelectionOfOrder = specialistSelectionOfOrder;
    }
}
