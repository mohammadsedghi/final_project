package com.example.finalproject_phase2.dto.ordersDto;

import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.SubDuty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersDtoWithCustomerAndSubDuty {
    Customer customer;
    SubDuty subDuty;
}
