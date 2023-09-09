package com.example.finalproject_phase2.dto.ordersDto;

import com.example.finalproject_phase2.entity.Address;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersDto {

    Customer customer;

    Specialist specialist;

    SubDuty subDuty;
    @NotNull(message = "proposedPrice must be have value")
    Double proposedPrice;
    @NotNull(message = "description must be have value")
    String description;
    @NotNull(message = "DateOfWork must be have value")
    LocalDate DateOfWork;
    @NotNull(message = "timeOfWork must be have value")
    LocalTime timeOfWork;

    Address address;

    OrderStatus orderStatus;
@Builder
    public OrdersDto(Customer customer,Specialist specialist, SubDuty subDuty, Double proposedPrice, String description, LocalDate dateOfWork, LocalTime timeOfWork, Address address, OrderStatus orderStatus) {
        this.customer = customer;
        this.specialist=specialist;
        this.subDuty = subDuty;
        this.proposedPrice = proposedPrice;
        this.description = description;
        DateOfWork = dateOfWork;
        this.timeOfWork = timeOfWork;
        this.address = address;
        this.orderStatus = orderStatus;
    }
}
