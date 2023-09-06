package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.entity.CustomerComments;
import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.service.CustomerCommentsService;
import com.example.finalproject_phase2.service.OrdersService;
import com.example.finalproject_phase2.service.SpecialistService;
import com.example.finalproject_phase2.mapper.CustomerCommentsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerCommentsServiceImplTest {
    @Autowired
    CustomerCommentsService customerCommentsService;
    @Autowired
    OrdersService ordersService;
    @Autowired
    SpecialistService specialistService;
    @Autowired
    CustomerCommentsMapper customerCommentsMapper;
    @BeforeEach
    void setUp() {
    }

    @Test
    void submitCustomerCommentsService() {

        Optional<Orders> order = ordersService.findById(1l);
        CustomerComments customerComments= CustomerComments.builder()
                .orders(order.get())
                .description("customerComments")
                .score(3)
                .build();

 assertEquals(true,customerCommentsService.submitCustomerCommentsService(
         customerCommentsMapper.customerCommentsToCustomerCommentsDto(customerComments)));

    }
    @Test
    void searchNumberOFCustomerComments(){
        //CustomerComments customerComments=customerCommentsService.findById(1l).get();
        Specialist specialist = specialistService.findByEmail("ali@gmail.com");
        Integer number = customerCommentsService.findNumberOFCustomerCommentsThatSpecialistIsExist(specialist);
        System.out.println(number);
    }
//    @Test
//    void springVersion(){
//        assertEquals("5.1.10.RELEASE", SpringVersion.getVersion());
//    }
}