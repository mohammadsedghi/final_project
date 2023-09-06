package com.example.finalproject_phase2.dto.subDutyDto;

import com.example.finalproject_phase2.entity.SubDuty;
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


    SubDuty subDuty;
    @NotNull(message = "this field must be have value")
    @Pattern(message = "province must be just letters",regexp = "^[a-zA-Z]+$")
    @Length(message ="lastName must be 100 character",max = 100)
    String description;
}
