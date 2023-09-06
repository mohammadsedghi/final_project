package com.example.finalproject_phase2.mapper;

import com.example.finalproject_phase2.dto.specialistSuggestionDto.SpecialistSuggestionDto;
import com.example.finalproject_phase2.entity.SpecialistSuggestion;
import org.mapstruct.Mapper;

import java.util.Collection;
@Mapper
public interface SpecialistSuggestionMapper {
      SpecialistSuggestionDto specialistSuggestionToSpecialistsuggestionDto(SpecialistSuggestion specialistSuggestion);
    Collection<SpecialistSuggestionDto>
    specialistSuggestionCollectionToSpecialistSuggestionCollectionDto(Collection<SpecialistSuggestion> specialistSuggestionCollection);


}
