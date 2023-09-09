package com.example.finalproject_phase2.dto.subDutyDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SubDutyDto {
    String dutyName;
    @NotNull(message = "name must be have value")
    @Pattern(message = "name must be just letters",regexp = "^[a-zA-Z]+$")
    @Length(message ="name must be 100 character",max = 100)
    String name;
    @NotNull(message = "basePrice of SubDuty must be have value")
     @Positive(message = "base price must be positive")
    Double basePrice;
    @NotNull(message = "this field must be have value")
    @Pattern(message = "province must be just letters",regexp = "^[a-zA-Z]+$")
    @Length(message ="lastName must be 100 character",max = 100)
    String description;





}
