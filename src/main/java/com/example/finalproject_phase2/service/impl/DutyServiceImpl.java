package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.repository.DutyRepository;
import com.example.finalproject_phase2.service.DutyService;
import com.example.finalproject_phase2.mapper.DutyMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DutyServiceImpl implements DutyService {
    private final DutyRepository dutyRepository;
    private final DutyMapper dutyMapper;
    CheckValidation checkValidation = new CheckValidation();

    @Autowired
    public DutyServiceImpl(DutyRepository dutyRepository, DutyMapper dutyMapper) {
        this.dutyRepository = dutyRepository;
        this.dutyMapper = dutyMapper;
    }

    @Override
    public DutyDto addDuty(DutyDto dutyDto) {

        try {
            if (!checkValidation.isValid(dutyDto)) {
                throw new CustomException("this duty  is invalid");
            }
            dutyRepository.findAll().forEach(duty1 -> {
                if (duty1.getName().equals(dutyDto.getName())) {
                    throw new CustomException("this duty name is exist");
                }
            });
            dutyRepository.save(dutyMapper.dutyDtoToDuty(dutyDto));
            return dutyDto;
        } catch (CustomException ce) {
            return new DutyDto();

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
}
