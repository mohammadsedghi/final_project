package com.example.finalproject_phase2.service.impl;


import com.example.finalproject_phase2.dto.customerDto.*;
import com.example.finalproject_phase2.service.CustomerService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceImplTest {

    @Autowired
    CustomerService customerService;
    CustomerDto customerDto;
    MotherObject motherObject;
    CustomerLoginDto customerLoginDto;
    CustomerChangePasswordDto customerChangePasswordDto;

    @BeforeEach
    void setUp() {
        motherObject = new MotherObject();
        customerDto = new CustomerDto();
        customerLoginDto = new CustomerLoginDto();
        customerChangePasswordDto = new CustomerChangePasswordDto();
    }

    @Test
    @Order(1)
    void findByEmail() {
        // assertEquals(throw new CustomException("transaction error"),customerService.findByEmail(motherObject.getDuplicateEmail());
        assertTrue(customerService.findByEmail(motherObject.getDuplicateEmail()).isPresent());


    }
    @Test
    @Order(2)
    void searchCustomer(){
        CustomerSearchDto customerDto=new CustomerSearchDto();
        customerDto.setEmail("mahan@gmail.com");
        customerDto.setFirstName("reza");
        customerDto.setLastName("akbar");
        customerDto.setNationalId("4560116815");
        List<CustomerResult> customers =customerService.searchCustomer(customerDto);
        System.out.println("cccccccccc"+customers.size());
        for (CustomerResult c:customers
             ) {
            System.out.println(c.getEmail());
        }
    }
}