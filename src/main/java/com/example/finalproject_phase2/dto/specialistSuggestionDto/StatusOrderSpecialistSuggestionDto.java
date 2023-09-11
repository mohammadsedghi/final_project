package com.example.finalproject_phase2.dto.specialistSuggestionDto;

import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.SpecialistSuggestion;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusOrderSpecialistSuggestionDto {
    @NotNull(message = "ordersId must be have value")
    Long ordersId;
    @NotNull(message = "specialistSuggestionId must be have value")
    Long specialistSuggestionId;
}
