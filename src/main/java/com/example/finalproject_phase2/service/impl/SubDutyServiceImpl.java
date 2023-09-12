package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.custom_exception.CustomNumberFormatException;
import com.example.finalproject_phase2.dto.dutyDto.DutyNameDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDtoDescription;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyNameDto;
import com.example.finalproject_phase2.entity.Duty;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.repository.SubDutyRepository;
import com.example.finalproject_phase2.service.DutyService;
import com.example.finalproject_phase2.service.SubDutyService;
import com.example.finalproject_phase2.mapper.DutyMapper;
import com.example.finalproject_phase2.mapper.SubDutyMapper;
import com.example.finalproject_phase2.util.validation.CheckValidation;
import com.example.finalproject_phase2.util.validation.CustomRegex;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service

public class SubDutyServiceImpl implements SubDutyService {
    private final SubDutyRepository subDutyRepository;
    private final DutyService dutyService;
    private final SubDutyMapper subDutyMapper;
    private final DutyMapper dutyMapper;
    SubDuty subDuty;
    CheckValidation checkValidation = new CheckValidation();

    public SubDutyServiceImpl(SubDutyRepository subDutyRepository, DutyService dutyService, SubDutyMapper subDutyMapper, DutyMapper dutyMapper) {
        this.subDutyRepository = subDutyRepository;
        this.dutyService = dutyService;
        this.subDutyMapper = subDutyMapper;
        this.dutyMapper = dutyMapper;
    }

    @Override
    public SubDutyDto addSubDuty(SubDutyDto subDutyDto) {
        try {
            if (!checkValidation.isValid(subDutyDto)) {
                throw new CustomException("input subDuty is invalid");
            }
            if (isExistSubDuty(subDutyDto.getName())){
                throw new CustomException("duplicate subDuty is invalid you should be inter difference name");
            }
            Duty duty = dutyService.findByNames(subDutyDto.getDutyName());
            SubDuty subDuty=new SubDuty(duty,subDutyDto.getName(),subDutyDto.getBasePrice(),subDutyDto.getDescription());
            SubDuty subDutyCandidate = subDutyRepository.save(subDuty);
            duty.getSubDuties().add(subDutyCandidate);
            dutyService.UpdateDuty(duty);
            return  subDutyDto;
        } catch (CustomException ce) {
            throw new CustomException(ce.getMessage());
        }
    }
    @Override
    public boolean isExistSubDuty(String name) {
       return subDutyRepository.isExistSubDuty(name).isPresent();
    }
    @Override
    public Set<SubDutyNameDto> showAllSubDutyOfDuty(DutyNameDto dutyNameDto) {
        Collection<SubDuty> subDuties = subDutyRepository.showSubDutyOfDuty(dutyService.findByNames(dutyNameDto.getName()));
        Set<SubDutyNameDto> subDutyNameDtoSet=new HashSet<>();
        for (SubDuty subDuty : subDuties
        ){
            SubDutyNameDto subDutyNameDto=new SubDutyNameDto();
            subDutyNameDto.setName(subDuty.getName());
            subDutyNameDtoSet.add(subDutyNameDto);
        }
        return subDutyNameDtoSet;
    }
    @Override
    public EditSubDutyDto editSubDutyPrice(EditSubDutyDto editSubDutyDto) {
        CustomRegex customRegex = new CustomRegex();
        try {
            SubDuty subDuty = findByNames(editSubDutyDto.getSubDutyName());
            if (customRegex.checkOneInputIsValid(editSubDutyDto.getBasePrice(), customRegex.getValidPrice())) {
                subDuty.setBasePrice(Double.parseDouble(editSubDutyDto.getBasePrice()));
                    subDutyRepository.save(subDuty);
                    return editSubDutyDto;
            } else throw new CustomNumberFormatException("input basePrice is invalid");
        } catch (CustomNumberFormatException cnf) {
          throw new CustomNumberFormatException(cnf.getMessage());
        }
    }
    @Override
    public EditSubDutyDtoDescription editSubDutyDescription(EditSubDutyDtoDescription editSubDutyDtoDescription) {
        CustomRegex customRegex = new CustomRegex();
        try {
            if (customRegex.checkOneInputIsValid(editSubDutyDtoDescription.getDescription(), customRegex.getValidStr())) {
                SubDuty subDuty = findByNames(editSubDutyDtoDescription.getSubDutyName());
                subDuty.setDescription(editSubDutyDtoDescription.getDescription());
                    subDutyRepository.save(subDuty);
           return editSubDutyDtoDescription;
            } else {
                throw new CustomException("input description is invalid");
            }
        } catch (CustomException ce) {
            throw new CustomException(ce.getMessage());
        }

    }



    @Override
    public SubDutyDto findByName(String name) {
        subDutyRepository.findByName(name).ifPresentOrElse(
              subDuty1 ->{subDuty=subDuty1;}
              ,()->{subDuty=new SubDuty();}
       );
        return subDutyMapper.subDutyToSubDutyDto(subDuty);
    }
    @Override
    public SubDuty findByNames(String name) {
        subDutyRepository.findByName(name).ifPresentOrElse(
                subDuty1 ->{subDuty=subDuty1;}
                ,()->{subDuty=new SubDuty();}
        );
        return subDuty;
    }
    @Override
    public SubDuty findById(Long id){
        return subDutyRepository.findById(id).get();
    }
}
