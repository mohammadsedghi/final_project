package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.service.DutyService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DutyServiceImplTest {
    @Autowired
    DutyService dutyService;
    MotherObject motherObject;
    DutyDto dutyDto;
    @BeforeEach
    void setUp() {
      motherObject=new MotherObject();
      dutyDto=new DutyDto();
    }

    @Test
    @Order(1)
    void addDuty() {
        assertEquals(motherObject.getValidDutyDto(),dutyService.addDuty(motherObject.getValidDutyDto()));
    }

    @Test
    @Order(2)
    void findAllByDuties() {
        List<DutyDto> dtoList=new ArrayList<>(motherObject.setOfDuty());
        List<DutyDto> dtoListTwo=new ArrayList<>(dutyService.findAllByDuties());
      //assertEquals(motherObject.setOfDuty(),dutyService.findAllByDuties());
      assertEquals(dtoList.get(0).getName(),dtoListTwo.get(0).getName());
    }
    @Test
    @Order(3)
    void findDutyByName() {
        assertEquals("AAA",dutyService.findByName("AAA").getName());
    }
}