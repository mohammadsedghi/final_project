package com.example.finalproject_phase2.dto.specialistDto;

import com.example.finalproject_phase2.entity.Specialist;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpecialistScoreDto {
    Specialist specialist;
    Integer score;
}
