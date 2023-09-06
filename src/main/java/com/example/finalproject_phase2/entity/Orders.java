package com.example.finalproject_phase2.entity;

import com.example.finalproject_phase2.base.BaseEntity;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Orders extends BaseEntity<Long> {
    @ManyToOne
    Customer customer;
    @ManyToOne
    Specialist specialist;
    @ManyToOne
    SubDuty subDuty;
    @NotNull(message = "proposedPrice must be have value")
    Double proposedPrice;
    @NotNull(message = "description must be have value")
    String description;
    @NotNull(message = "DateOfWork must be have value")
    LocalDate DateOfWork;
    @NotNull(message = "timeOfWork must be have value")
    LocalTime timeOfWork;
    @OneToOne
    Address address;
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

}
