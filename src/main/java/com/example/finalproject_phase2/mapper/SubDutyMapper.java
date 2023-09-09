package com.example.finalproject_phase2.mapper;

import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.entity.SubDuty;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.Set;
@Mapper
public interface SubDutyMapper {
     SubDuty subDutyDtoToSubDuty(SubDutyDto subDutyDto);
     SubDutyDto subDutyToSubDutyDto(SubDuty subDuty);

    Set<SubDutyDto> collectionOfSubDutyToSetOfSubDutyDto(Collection<SubDuty> SubDuties);
}
