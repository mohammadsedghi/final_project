package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.dto.customerDto.CustomerDtoEmail;
import com.example.finalproject_phase2.dto.ordersDto.*;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyNameDto;
import com.example.finalproject_phase2.entity.*;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import com.example.finalproject_phase2.mapper.AddressMapper;
import com.example.finalproject_phase2.service.*;
import com.example.finalproject_phase2.mapper.CustomerMapper;
import com.example.finalproject_phase2.mapper.SubDutyMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrdersServiceImplTest {
    @Autowired
    OrdersService ordersService;
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    SubDutyMapper subDutyMapper;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    SpecialistService specialistService;
    @Autowired
    SubDutyService subDutyService;
    @Autowired
    AddressService addressService;
    MotherObject motherObject;
    @BeforeEach
    void setUp() {
        motherObject=new MotherObject();
    }

    @Test
    @Order(1)
    void submitOrder() {
//        Optional<Customer> customer = customerService.findByEmail("");
//        Specialist specialist = specialistService.findByEmail("");
//        SubDuty subDuty = subDutyMapper.subDutyDtoToSubDuty(subDutyService.findByName("CD"));
//        Address address = addressService.createAddress("");
//        Orders orders=new Orders(customer.get(),specialist,subDuty,13d,"orderrrrr",
//     LocalDate.now(), LocalTime.now(),address, OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION);

        SubmitOrderDto  order = SubmitOrderDto.builder()
                .customerEmail("mahan@gmail.com")
                .specialistEmail("ali@gmail.com")
                .subDutyId(2l)
                .description("order")
                .address(addressMapper.addressDtoToAddress(motherObject.getValidAddressDto()))
                .proposedPrice("12")
                .year(2024).month(12).day(1)
                .timeOfWork("20-12-10")
                .build();

        ordersService.submitOrder(order);
    }

    @Test
    @Order(2)
    void showOrdersToSpecialist() {
        SubDutyNameDto subDutyNameDto=new SubDutyNameDto();
        subDutyNameDto.setName("CD");
        List<Orders> orders=new ArrayList<>(ordersService.showOrdersToSpecialist(subDutyNameDto));
        assertEquals("CD",orders.get(0).getSubDuty().getName());
    }

    @Test
    @Order(3)
    void updateOrderToNextLevel() {
        OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus=new OrdersDtoWithOrdersStatus();
        Orders orders = ordersService.findById(1l).get();
        ordersDtoWithOrdersStatus.setOrderStatus( OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION);
        ordersDtoWithOrdersStatus.setOrdersId(orders.getId());
        OrderStatus orderStatus = ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus).getOrderStatus();
        assertNotEquals(orders.getOrderStatus(),orderStatus);
    }

    @Test
    @Order(4)
    void findOrdersWithThisCustomerAndSubDuty() {
        OrdersDtoWithCustomerAndSubDuty ordersDtoWithCustomerAndSubDuty=new OrdersDtoWithCustomerAndSubDuty();
        ordersDtoWithCustomerAndSubDuty.setCustomer(customerService.findByEmail("mahan@gmail.com").get());
        ordersDtoWithCustomerAndSubDuty.setSubDuty(subDutyMapper.subDutyDtoToSubDuty(subDutyService.findByName("CD")));
        OrdersDto orderDto = ordersService.findOrdersWithThisCustomerAndSubDuty(ordersDtoWithCustomerAndSubDuty);
        assertEquals(orderDto,ordersService.findOrdersWithThisCustomerAndSubDuty(ordersDtoWithCustomerAndSubDuty));
    }

    @Test
    @Order(5)
    void findOrdersInStatusWaitingForSpecialistSuggestion(){
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        CustomerDtoEmail customerDtoEmail=new CustomerDtoEmail();
        customerDtoEmail.setEmail(customer.getEmail());
        List<Orders>orders=new ArrayList<>(ordersService.findOrdersInStatusWaitingForSpecialistSuggestion(customerDtoEmail));
        assertEquals(customer,orders.get(0).getCustomer());
    }

    @Test
    @Order(6)
    void findOrdersInStatusWaitingForSpecialistSelection() {
        OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus=new OrdersDtoWithOrdersStatus();
        Orders orders = ordersService.findById(1l).get();
        ordersDtoWithOrdersStatus.setOrderStatus( OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION);
        ordersDtoWithOrdersStatus.setOrdersId(orders.getId());
        ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        CustomerDtoEmail customerDtoEmail=new CustomerDtoEmail();
        customerDtoEmail.setEmail(customer.getEmail());
        List<Orders>ordersList=new ArrayList<>(ordersService.findOrdersInStatusWaitingForSpecialistSelection(customerDtoEmail));
        assertEquals(customer,ordersList.get(0).getCustomer());
    }

    @Test
    @Order(7)
    void findOrdersInStatusWaitingForSpecialistToWorkplace() {
        OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus=new OrdersDtoWithOrdersStatus();
        Orders orders = ordersService.findById(1l).get();
        ordersDtoWithOrdersStatus.setOrderStatus(OrderStatus.ORDER_WAITING_FOR_SPECIALIST_TO_WORKPLACE);
       ordersDtoWithOrdersStatus.setOrdersId(orders.getId());
        ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        CustomerDtoEmail customerDtoEmail=new CustomerDtoEmail();
        customerDtoEmail.setEmail(customer.getEmail());
        List<Orders>ordersList=new ArrayList<>(ordersService.findOrdersInStatusWaitingForSpecialistToWorkplace(customerDtoEmail));
        assertEquals(customer,ordersList.get(0).getCustomer());
    }

    @Test
    @Order(8)
    void findOrdersInStatusStarted() {
        OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus=new OrdersDtoWithOrdersStatus();
        Orders orders = ordersService.findById(1l).get();
        ordersDtoWithOrdersStatus.setOrderStatus(OrderStatus.ORDER_STARTED);
        ordersDtoWithOrdersStatus.setOrdersId(orders.getId());
        ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        CustomerDtoEmail customerDtoEmail=new CustomerDtoEmail();
        customerDtoEmail.setEmail(customer.getEmail());
        List<Orders>ordersList=new ArrayList<>(ordersService.findOrdersInStatusStarted(customerDtoEmail));
        assertEquals(customer,ordersList.get(0).getCustomer());
    }

    @Test
    @Order(9)
    void findOrdersInStatusPaid() {
        OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus=new OrdersDtoWithOrdersStatus();
        Orders orders = ordersService.findById(1l).get();
        ordersDtoWithOrdersStatus.setOrderStatus(OrderStatus.ORDER_PAID);
        ordersDtoWithOrdersStatus.setOrdersId(orders.getId());
        ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        CustomerDtoEmail customerDtoEmail=new CustomerDtoEmail();
        customerDtoEmail.setEmail(customer.getEmail());
        List<Orders>ordersList=new ArrayList<>(ordersService.findOrdersInStatusPaid(customerDtoEmail));
        assertEquals(customer,ordersList.get(0).getCustomer());
    }

    @Test
    @Order(10)
    void findOrdersInStatusDone() {
        OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus=new OrdersDtoWithOrdersStatus();
        Orders orders = ordersService.findById(1l).get();
        ordersDtoWithOrdersStatus.setOrderStatus(OrderStatus.ORDER_DONE);
        ordersDtoWithOrdersStatus.setOrdersId(orders.getId());
        ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
        Customer customer = customerService.findByEmail("mahan@gmail.com").get();
        CustomerDtoEmail customerDtoEmail=new CustomerDtoEmail();
        customerDtoEmail.setEmail(customer.getEmail());
        List<Orders>ordersList=new ArrayList<>(ordersService.findOrdersInStatusDone(customerDtoEmail));
        assertEquals(customer,ordersList.get(0).getCustomer());
        ordersDtoWithOrdersStatus.setOrderStatus(  OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SUGGESTION);
        ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
    }
    @Test
    @Order(11)
    void findOrdersById() {
        ordersService.findById(1l);
    }
}