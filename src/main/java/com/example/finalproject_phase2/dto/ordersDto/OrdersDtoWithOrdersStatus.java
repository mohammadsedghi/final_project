package com.example.finalproject_phase2.dto.ordersDto;

import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersDtoWithOrdersStatus {
    @NotNull(message = "order must be have value")
    Orders orders;
    @NotNull(message = "order must be have value")
    OrderStatus orderStatus;
}
