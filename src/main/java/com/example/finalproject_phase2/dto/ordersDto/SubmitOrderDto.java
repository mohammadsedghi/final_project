package com.example.finalproject_phase2.dto.ordersDto;

import com.example.finalproject_phase2.entity.Address;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitOrderDto {
    String customerEmail;
    String specialistEmail;
//    String subDutyName;
    Long subDutyId;
    Address address;
    String proposedPrice;
    String description;
    int year ;
    int month ;
    int day ;
//    @NotNull(message = "DateOfWork must be have value")
//    String DateOfWork;
    @NotNull(message = "timeOfWork must be have value")
    String timeOfWork;


}
