package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDtoEmail;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.dto.ordersDto.*;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyNameDto;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrdersService {
    OrdersDto submitOrder(SubmitOrderDto submitOrderDto);
    Collection<Orders> showOrdersToSpecialist(SubDutyNameDto subDutyNameDtoDto );
    Orders updateOrderToNextLevel(OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus);
    OrdersDto findOrdersWithThisCustomerAndSubDuty(OrdersDtoWithCustomerAndSubDuty ordersDtoWithCustomerAndSubDuty) ;
    Collection<Orders> findOrdersInStatusWaitingForSpecialistSuggestion(CustomerDtoEmail customerDtoEmail);
    Collection<Orders> findOrdersInStatusWaitingForSpecialistSelection(CustomerDtoEmail customerDtoEmail);
    Collection<Orders> findOrdersInStatusWaitingForSpecialistToWorkplace(CustomerDtoEmail customerDtoEmail);
    Collection<Orders> findOrdersInStatusStarted(CustomerDtoEmail customerDtoEmail);
    Collection<Orders> findOrdersInStatusPaid(CustomerDtoEmail customerDtoEmail);
    Collection<Orders> findOrdersInStatusDone(CustomerDtoEmail customerDtoEmail);
//    List<OrdersResult> searchInDuty(String dutyName );
    List<OrdersResult> searchInDuty(OrdersAdvanceSearchParameter ordersAdvanceSearchParameter );
    Long numberOfOrders(String email ,String userType );
    Map<OrderStatus,List<OrdersResult>> showHistoryOrders(CustomerDtoEmail customerDtoEmail);
    List<OrderStatus>toListOrdersStatus();
     ResponseEntity<List<OrdersResult>> getListResponseEntity(List<OrdersResult> ordersResults, Collection<Orders> ordersCollection);
     Collection<Orders> findOrdersForSpecialistInStatus(CustomerDtoEmail customerDtoEmail,OrderStatus orderStatus) ;


    Optional<Orders> findById(Long id);
}
