package com.example.finalproject_phase2.dto.ordersDto;

import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class OrdersAdvanceSearchParameter {
    String dutyName;
    String subDutyName;
    String email;
    LocalDate dateOfWorkStart;
    LocalDate dateOfWorkEnd;
    OrderStatus orderStatus;

}
