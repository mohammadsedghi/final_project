package com.example.finalproject_phase2.mapper;

import com.example.finalproject_phase2.dto.ordersDto.OrdersDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDtoWithOrdersStatus;
import com.example.finalproject_phase2.entity.Orders;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper()
public interface OrdersMapper {
    Orders ordersDtoToOrders(OrdersDto ordersDto);
    OrdersDto ordersToOrdersDto(Orders orders);
    Collection<OrdersDto> collectionOrdersToCollectionOrdersDto(Collection<Orders> ordersCollection);
    OrdersDtoWithOrdersStatus OrdersDtoToOrdersDtoWithOrdersStatus(OrdersDto ordersDto);
}
