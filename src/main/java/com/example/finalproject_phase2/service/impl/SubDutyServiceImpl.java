package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.custom_exception.CustomNumberFormatException;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDtoDescription;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.repository.SubDutyRepository;
import com.example.finalproject_phase2.service.DutyService;
import com.example.finalproject_phase2.service.SubDutyService;
import com.example.finalproject_phase2.mapper.DutyMapper;
import com.example.finalproject_phase2.mapper.SubDutyMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import com.example.finalproject_phase2.util.CustomRegex;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

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
                throw new CustomException("duplicate subDuty is invalid");
            }
            SubDuty subDuty = subDutyRepository.save(subDutyMapper.subDutyDtoToSubDuty(subDutyDto));
          //  Set<SubDuty> subDuties = new HashSet<>();
            subDuty.getDuty().getSubDuties().add(subDuty);
            //subDuties.add(subDuty);
            dutyService.addDuty(dutyMapper.dutyToDutyDto(subDuty.getDuty()));
            return  subDutyDto;
        } catch (CustomException ce) {
            return new SubDutyDto();
        }

    }
    @Override
    public Set<SubDuty> showAllSubDutyOfDuty(DutyDto dutyDto) {
 return new HashSet<>(subDutyRepository.showSubDutyOfDuty(dutyMapper.dutyDtoToDuty(dutyDto)));
    }
    @Override
    public SubDutyDto editSubDutyPrice(EditSubDutyDto editSubDutyDto) {
        CustomRegex customRegex = new CustomRegex();
        try {
            if (customRegex.checkOneInputIsValid(editSubDutyDto.getBasePrice(), customRegex.getValidPrice())) {
                SubDutyDto subDutyCandidate = findByName(editSubDutyDto.getSubDuty().getName());
                subDutyCandidate.setBasePrice(Double.parseDouble(editSubDutyDto.getBasePrice()));
                    subDutyRepository.save(subDutyMapper.subDutyDtoToSubDuty(subDutyCandidate));
                    return subDutyCandidate;
            } else throw new CustomNumberFormatException("input basePrice is invalid");
        } catch (CustomNumberFormatException cnf) {
           return new SubDutyDto();
        }
    }
    @Override
    public SubDutyDto editSubDutyDescription(EditSubDutyDtoDescription editSubDutyDtoDescription) {
        CustomRegex customRegex = new CustomRegex();
        try {
            if (customRegex.checkOneInputIsValid(editSubDutyDtoDescription.getDescription(), customRegex.getValidStr())) {
                SubDutyDto subDutyCandidate = findByName(editSubDutyDtoDescription.getSubDuty().getName());
                subDutyCandidate.setDescription(editSubDutyDtoDescription.getDescription());
                    subDutyRepository.save(subDutyMapper.subDutyDtoToSubDuty(subDutyCandidate));
           return subDutyCandidate;
            } else {
                throw new CustomException("input description is invalid");
            }
        } catch (CustomException ce) {
            return new SubDutyDto();
        }

    }

    @Override
    public boolean isExistSubDuty(String name) {
      AtomicBoolean flag= new AtomicBoolean(false);
            subDutyRepository.isExistSubDuty(name).ifPresent(
                    subDuty -> {
                       flag.set(true);
                    }
            );
        return flag.get();
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
