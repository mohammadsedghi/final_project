package com.example.finalproject_phase2.dto.specialistSuggestionDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Setter
@Getter
@AllArgsConstructor
public class SpecialistSuggestionResult {
    @NotNull(message = "specialistName must be have value")
    String specialistName;
    @NotNull(message = "specialistFamily must be have value")
    String specialistFamily;

    Integer score ;
    @NotNull(message = "proposedPrice must be have value")
    @Positive(message = "price must be positive")
    Double proposedPrice;
    @NotNull(message = "TimeOfStartWork must be have value")
    LocalTime TimeOfStartWork;
    @NotNull(message = "DateOfStartWork must be have value")
    LocalDate DateOfStartWork;
    @NotNull(message = "durationOfWorkPerHour must be have value")
    Integer durationOfWorkPerHour;


}
