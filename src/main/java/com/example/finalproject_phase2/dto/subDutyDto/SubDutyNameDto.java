package com.example.finalproject_phase2.dto.subDutyDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubDutyNameDto {
    @NotNull(message = "name of sub duty must be have value")
    String name ;
}
