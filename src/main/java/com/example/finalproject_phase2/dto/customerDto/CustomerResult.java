package com.example.finalproject_phase2.dto.customerDto;

import com.example.finalproject_phase2.dto.personDto.PersonResult;
import com.example.finalproject_phase2.entity.Wallet;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResult{
    String name;
    String family;
    String email;



}
