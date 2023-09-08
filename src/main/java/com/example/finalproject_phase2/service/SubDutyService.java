package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDtoDescription;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.entity.Duty;
import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.SubDuty;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface SubDutyService {
    SubDutyDto addSubDuty(SubDutyDto subDutyDto);
    Set<SubDuty> showAllSubDutyOfDuty(DutyDto dutyDto);
    SubDutyDto editSubDutyPrice(EditSubDutyDto editSubDutyDto);
    SubDutyDto editSubDutyDescription(EditSubDutyDtoDescription editSubDutyDtoDescription);
    boolean isExistSubDuty(String name);
    SubDutyDto findByName(String name);
    SubDuty findByNames(String name);
    SubDuty findById(Long id);
}
