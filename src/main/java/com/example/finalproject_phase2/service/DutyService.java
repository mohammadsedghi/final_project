package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.entity.Duty;

import java.util.Collection;
import java.util.Set;

public interface DutyService {
    DutyDto addDuty(Duty duty);
    Set<DutyDto> findAllByDuties();
    DutyDto UpdateDuty(Duty duty);
    DutyDto findByName(String name);
    Duty findByNames(String name);
}
