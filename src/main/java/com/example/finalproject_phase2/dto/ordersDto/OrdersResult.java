package com.example.finalproject_phase2.dto.ordersDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResult {
    String specialistLastName;
    String customerLastName;
    LocalDate dateOfWork;
    Double price;

}
