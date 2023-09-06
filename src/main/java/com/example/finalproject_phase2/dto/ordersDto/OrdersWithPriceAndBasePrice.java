package com.example.finalproject_phase2.dto.ordersDto;

import com.example.finalproject_phase2.entity.Orders;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrdersWithPriceAndBasePrice {
    Orders orders;
    String priceOfOrders;

}
