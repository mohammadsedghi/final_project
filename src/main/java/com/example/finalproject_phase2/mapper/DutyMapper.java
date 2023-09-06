package com.example.finalproject_phase2.mapper;

import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.entity.Duty;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.Set;
@Mapper
public interface DutyMapper {
    Duty dutyDtoToDuty(DutyDto dutyDto);

     DutyDto dutyToDutyDto(Duty duty);


    Set<Duty> collectionOfDutyDtoToSetOfDuty(Collection<DutyDto> duties);
     Set<DutyDto> collectionOfDutyToSetOfDutyDto(Collection<Duty> duties);

}
