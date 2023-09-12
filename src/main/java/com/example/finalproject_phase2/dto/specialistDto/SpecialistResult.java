package com.example.finalproject_phase2.dto.specialistDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpecialistResult {
    String name;
    String family;
    String email;

}
