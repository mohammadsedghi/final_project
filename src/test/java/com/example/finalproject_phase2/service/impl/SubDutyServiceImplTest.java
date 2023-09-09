package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.dto.dutyDto.DutyNameDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDtoDescription;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyNameDto;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.service.DutyService;
import com.example.finalproject_phase2.service.SubDutyService;
import com.example.finalproject_phase2.mapper.DutyMapper;
import com.example.finalproject_phase2.mapper.SubDutyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubDutyServiceImplTest {
    @Autowired
    SubDutyService subDutyService;
    @Autowired
    DutyMapper dutyMapper;
    @Autowired
    SubDutyMapper subDutyMapper;
  @Autowired
    DutyService dutyService;
    MotherObject motherObject;
    SubDutyDto subDutyDto;
    EditSubDutyDto editSubDutyDto;
    static SubDutyDto validSubDutyDto;
    @BeforeEach
    void setUp() {
        motherObject =new MotherObject();
        subDutyDto =new SubDutyDto();
        editSubDutyDto=new EditSubDutyDto();
    }


    @Test
    void addSubDuty() {
        validSubDutyDto = motherObject.getValidSubDutyDto();
        assertEquals(validSubDutyDto,subDutyService.addSubDuty(validSubDutyDto));
    }

    @Test
    void showAllSubDutyOfDuty() {
     Set<SubDuty> subDuties=new HashSet<>();
     subDuties.add(subDutyMapper.subDutyDtoToSubDuty(subDutyService.findByName("CD")));
        List<SubDuty> list1=new ArrayList<>(subDuties);
        DutyNameDto dutyNameDto=new DutyNameDto();
        dutyNameDto.setName("AAA");
        List<SubDutyNameDto> list2=new ArrayList<>(subDutyService.showAllSubDutyOfDuty( dutyNameDto));
        assertEquals(list1.get(0).getName(),list2.get(0).getName());
    }

    @Test
    void editSubDutyPrice() {
        SubDuty subDuty = subDutyService.findByNames("AB");
        EditSubDutyDto editSubDutyDto=new EditSubDutyDto();
        editSubDutyDto.setSubDutyName(subDuty.getName());
        editSubDutyDto.setBasePrice("500");
        assertEquals(editSubDutyDto.getBasePrice(),subDutyService.editSubDutyPrice(editSubDutyDto).getBasePrice().toString());
    }
//    @Test
//    void findByName() {
//       assertEquals("202",subDutyService.findByName("AB"));
//    }

    @Test
    void editSubDutyDescription() {
        SubDutyDto subDutyServiceByName = subDutyService.findByName("AB");
        EditSubDutyDtoDescription editSubDutyDtoDescription=new EditSubDutyDtoDescription();
        editSubDutyDtoDescription.setSubDutyName(subDutyServiceByName.getDutyName());
        editSubDutyDtoDescription.setDescription("hhh");
        assertEquals(editSubDutyDtoDescription,subDutyService.
                editSubDutyDescription(editSubDutyDtoDescription));
    }

    @Test
    void isExistSubDutyTrueResult() {
        assertTrue(subDutyService.isExistSubDuty("AB"));
    }
    @Test
    void isExistSubDutyFalseResult() {
        assertFalse(subDutyService.isExistSubDuty("ppp"));
    }
}