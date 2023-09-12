package com.example.finalproject_phase2.dto.specialistSuggestionDto;

import com.example.finalproject_phase2.entity.enumeration.SpecialistSelectionOfOrder;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class SuggestionStatusAndIdDto {
    @NotNull(message = "specialistSelectionOfOrder must be have value")
    SpecialistSelectionOfOrder specialistSelectionOfOrder;
    @NotNull(message = "id must be have value")
    Long id;
}
