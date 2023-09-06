package com.example.finalproject_phase2.dto.subDutyDto;
import com.example.finalproject_phase2.entity.SubDuty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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

    SubDuty subDuty;

}
