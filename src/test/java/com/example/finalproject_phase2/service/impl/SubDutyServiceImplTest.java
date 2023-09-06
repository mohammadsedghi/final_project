package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDtoDescription;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
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
        List<SubDuty> list2=new ArrayList<>(subDutyService.showAllSubDutyOfDuty( dutyService.findByName("AAA")));

// assertEquals(subDuties,subDutyService.showAllSubDutyOfDuty(DutyMapper.dutyToDutyDto(dutyService.findByName("AAA"))));
      // assertEquals(subDuties,subDutyService.showAllSubDutyOfDuty( dutyService.findByName("AAA")));
        assertEquals(list1.get(0).getName(),list2.get(0).getName());
    }

    @Test
    void editSubDutyPrice() {
        SubDutyDto subDutyServiceByName = subDutyService.findByName("AB");
        EditSubDutyDto editSubDutyDto1=new EditSubDutyDto();
        editSubDutyDto1.setSubDuty(subDutyMapper.subDutyDtoToSubDuty(subDutyServiceByName));
        editSubDutyDto1.setBasePrice("500");
        assertEquals(subDutyServiceByName,subDutyService.editSubDutyPrice(editSubDutyDto1));
    }
//    @Test
//    void findByName() {
//       assertEquals("202",subDutyService.findByName("AB"));
//    }

    @Test
    void editSubDutyDescription() {
        SubDutyDto subDutyServiceByName = subDutyService.findByName("AB");
        EditSubDutyDtoDescription editSubDutyDtoDescription=new EditSubDutyDtoDescription();
        editSubDutyDtoDescription.setSubDuty(subDutyMapper.subDutyDtoToSubDuty(subDutyServiceByName));
        editSubDutyDtoDescription.setDescription("hhh");
        assertEquals(subDutyServiceByName,subDutyService.
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