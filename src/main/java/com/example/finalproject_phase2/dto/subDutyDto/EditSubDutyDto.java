package com.example.finalproject_phase2.dto.subDutyDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class EditSubDutyDto {

    @NotNull(message = "basePrice of SubDuty must be have value")
    String basePrice;
    @NotNull(message = "subDutyName of SubDuty must be have value")
    @Pattern(message = "subDutyName must be just letters",regexp = "^[a-zA-Z]+$")
    String subDutyName;


}
