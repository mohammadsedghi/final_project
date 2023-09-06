package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.dto.addressDto.AddressDto;
import com.example.finalproject_phase2.service.AddressService;
import com.example.finalproject_phase2.mapper.AddressMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressServiceImplTest {
    @Autowired
    AddressService addressService;
    @Autowired
    AddressMapper addressMapper;
    AddressDto addressDto;
    MotherObject motherObject;

    @BeforeEach
    void setUp() {
        addressDto=new AddressDto();
        motherObject=new MotherObject();
    }


    @Test
    @Order(1)
    void createAddress() {
        assertEquals(addressMapper.addressDtoToAddress(motherObject.getValidAddressDto()),addressService.createAddress(motherObject.getValidAddressDto()));
    }
}