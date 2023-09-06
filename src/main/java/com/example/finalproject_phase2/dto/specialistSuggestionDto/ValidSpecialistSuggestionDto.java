package com.example.finalproject_phase2.dto.specialistSuggestionDto;

import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.SubDuty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidSpecialistSuggestionDto {
    Specialist specialist;
    Orders orders;
    Integer workTimePerHour;

    int hour;
    int minutes;
    int day;
    int month;
    int year;
    SubDuty subDuty;
    Double proposedPrice;
}
