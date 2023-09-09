package com.example.finalproject_phase2.dto.ordersDto;

import com.example.finalproject_phase2.entity.Address;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitOrderDto {
    String customerEmail;
    String specialistEmail;
    Long subDutyId;
    Address address;
    String proposedPrice;
    String description;
    int year ;
    int month ;
    int day ;
    @NotNull(message = "timeOfWork must be have value")
    String timeOfWork;


}
