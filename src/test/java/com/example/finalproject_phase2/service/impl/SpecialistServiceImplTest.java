package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.dto.specialistDto.SpecialistChangePasswordDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistEmailDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistScoreDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistSubDutyDto;
import com.example.finalproject_phase2.entity.Duty;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.mapper.DutyMapper;
import com.example.finalproject_phase2.mapper.SpecialistMapper;
import com.example.finalproject_phase2.mapper.SubDutyMapper;
import com.example.finalproject_phase2.service.DutyService;
import com.example.finalproject_phase2.service.SpecialistService;
import com.example.finalproject_phase2.service.SubDutyService;
import com.example.finalproject_phase2.service.WalletService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpecialistServiceImplTest {
    @Autowired
    SpecialistService specialistService;
    @Autowired
    DutyService dutyService;
    @Autowired
    DutyMapper dutyMapper;
    @Autowired
    SubDutyMapper subDutyMapper;
    @Autowired
    SubDutyService subDutyService;
    @Autowired
    WalletService walletService;
    @Autowired
    SpecialistMapper specialistMapper;
    Duty duty;
    SubDuty subDuty;
    MotherObject motherObject;

    @BeforeEach
    void setUp() {
        motherObject = new MotherObject();
    }


    @Test
    void confirmSpecialistByAdmin() {
        SpecialistEmailDto specialistEmailDto=new SpecialistEmailDto("ali@gmail.com");
        specialistService.confirmSpecialistByAdmin(specialistEmailDto);
    }

    @Test

    void addSpecialistToSubDuty() {
        SpecialistSubDutyDto specialistSubDutyDto=new SpecialistSubDutyDto();
        specialistSubDutyDto.setSpecialist(specialistService.findByEmail("ali@gmail.com"));
        specialistSubDutyDto.setSubDuty(subDutyMapper.subDutyDtoToSubDuty(subDutyService.findByName("AB")) );
   specialistService.addSpecialistToSubDuty(specialistSubDutyDto);
    }

    @Test
    void changePassword() {
        SpecialistChangePasswordDto specialistChangePasswordDto=new SpecialistChangePasswordDto();
        specialistChangePasswordDto.setEmail("ali@gmail.com");
        specialistChangePasswordDto.setOldPassword("123456al");
        specialistChangePasswordDto.setNewPassword("123456al");
       assertEquals(true, specialistService.changePassword(specialistChangePasswordDto.getEmail(),specialistChangePasswordDto.getNewPassword()));
    }

    @Test
    void removeSpecialistFromDuty() {
        specialistService.removeSpecialistFromDuty();
    }


    @Test
    void updateSpecialistScore(){
        Specialist specialist = specialistService.findByEmail("ali@gmail.com");
        SpecialistScoreDto specialistScoreDto=new SpecialistScoreDto();
        specialistScoreDto.setScore(2);
        specialistScoreDto.setSpecialist(specialist);
        assertEquals(specialistScoreDto.getScore(),specialistService.updateSpecialistScore(specialistScoreDto));
    }

}