package com.example.finalproject_phase2.dto.ordersDto;

import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersAdvanceSearchParameter {
    String dutyName;
    String subDutyName;
    String email;
    LocalDate dateOfWorkStart;
    LocalDate dateOfWorkEnd;
    OrderStatus orderStatus;

}
