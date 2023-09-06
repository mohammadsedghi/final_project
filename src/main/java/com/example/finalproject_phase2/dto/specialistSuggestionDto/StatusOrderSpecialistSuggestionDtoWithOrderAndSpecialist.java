package com.example.finalproject_phase2.dto.specialistSuggestionDto;

import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.Specialist;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist {
    Orders orders;
    Specialist specialist;


}
