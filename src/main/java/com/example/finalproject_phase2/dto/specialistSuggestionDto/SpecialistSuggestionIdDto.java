package com.example.finalproject_phase2.dto.specialistSuggestionDto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpecialistSuggestionIdDto {
    @NotNull(message = "id must be have value")
    Long id;
}
