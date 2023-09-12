package com.example.finalproject_phase2.dto.specialistSuggestionDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SuggestionDto {
    @NotNull(message = "specialistEmail must be have value")
    String specialistEmail;
    @NotNull(message = "ordersId must be have value")

    Long ordersId;
    @NotNull(message = "workTimePerHour must be have value")

    Integer workTimePerHour;
    @NotNull(message = "hour must be have value")

    int hour;
    @NotNull(message = "minutes must be have value")

    int minutes;
    @NotNull(message = "day must be have value")

    int day;
    @NotNull(message = "month must be have value")

    int month;
    @NotNull(message = "year must be have value")

    int year;
    @NotNull(message = "subDutyName must be have value")

    String subDutyName;
    @NotNull(message = "proposedPrice must be have value")

    Double proposedPrice;
}
