package com.example.finalproject_phase2.dto.subDutyDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class EditSubDutyDtoDescription {

    @NotNull(message = "subDutyName must be have value")
    @Pattern(message = "subDutyName must be just letters",regexp = "^[a-zA-Z]+$")
    String subDutyName;
    @NotNull(message = "description must be have value")
    @Pattern(message = "description must be just letters",regexp = "^[a-zA-Z]+$")
    @Length(message ="description must be 100 character",max = 100)
    String description;
}
