package com.example.finalproject_phase2.mapper;

import com.example.finalproject_phase2.dto.specialistDto.SpecialistDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistLogin;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistLoginDto;
import com.example.finalproject_phase2.entity.Specialist;
import org.mapstruct.Mapper;

@Mapper
public interface SpecialistMapper {
      Specialist specialistDtoToSpecialist(SpecialistDto specialistDto);
      SpecialistDto specialistToSpecialistDto(Specialist specialist);
      SpecialistLoginDto specialistLoginToSpecialistLoginDto(SpecialistLogin specialistLogin);
      SpecialistLogin specialistLoginDtoToSpecialistLogin(SpecialistLoginDto specialistLoginDto);

}
