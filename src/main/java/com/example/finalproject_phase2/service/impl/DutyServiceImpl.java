package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.entity.Duty;
import com.example.finalproject_phase2.repository.DutyRepository;
import com.example.finalproject_phase2.service.DutyService;
import com.example.finalproject_phase2.mapper.DutyMapper;
import com.example.finalproject_phase2.util.validation.CheckValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DutyServiceImpl implements DutyService {
    private final DutyRepository dutyRepository;
    private final DutyMapper dutyMapper;


    @Autowired
    public DutyServiceImpl(DutyRepository dutyRepository, DutyMapper dutyMapper) {
        this.dutyRepository = dutyRepository;
        this.dutyMapper = dutyMapper;
    }

    @Override
    public DutyDto addDuty(Duty duty) {

        try {
            dutyRepository.findAll().forEach(duty1 -> {
                if (duty1.getName().equals(duty.getName())) {
                    throw new CustomException("this duty name is exist");
                }
            });
            dutyRepository.save(duty);
            return dutyMapper.dutyToDutyDto(duty);
        } catch (CustomException ce) {
            throw new CustomException(ce.getMessage());
        }

    }
    @Override
    public DutyDto UpdateDuty(Duty duty) {

        try {
            dutyRepository.save(duty);
            return dutyMapper.dutyToDutyDto(duty);
        } catch (CustomException ce) {
            throw new CustomException(ce.getMessage());
        }

    }


    @Override
    public Set<DutyDto> findAllByDuties() {
        return dutyMapper.collectionOfDutyToSetOfDutyDto(dutyRepository.findAllByDuties());
    }

    @Override
    public DutyDto findByName(String name) {
        return dutyMapper.dutyToDutyDto(dutyRepository.findByName(name));
    }
    @Override
    public Duty findByNames(String name) {
        return dutyRepository.findByName(name);
    }
}
