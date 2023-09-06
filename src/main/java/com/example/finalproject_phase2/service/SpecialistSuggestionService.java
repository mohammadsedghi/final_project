package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDto;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.SpecialistSuggestionDto;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.StatusOrderSpecialistSuggestionDto;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.ValidSpecialistSuggestionDto;
import com.example.finalproject_phase2.entity.*;
import com.example.finalproject_phase2.entity.enumeration.SpecialistSelectionOfOrder;

import java.util.List;
import java.util.Optional;

public interface SpecialistSuggestionService {
    Boolean submitSpecialistSuggestion(SpecialistSuggestion specialistSuggestion);
    Boolean findSuggestWithThisSpecialistAndOrder(StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist);
    Boolean IsValidSpecialSuggestion(ValidSpecialistSuggestionDto validSpecialistSuggestionDto);
    List<SpecialistSuggestionDto> findCustomerOrderSuggestionOnPrice(CustomerDto customerDto);
    List<SpecialistSuggestionDto> findCustomerOrderSuggestionOnScoreOfSpecialist(CustomerDto customerDto);
    Boolean changeStatusOrderToWaitingForSpecialistToWorkplace(StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist);
    SpecialistSelectionOfOrder changeSpecialistSelectedOfOrder(SpecialistSelectionOfOrder specialistSelectionOfOrder);
    Boolean changeStatusOrderToStarted(StatusOrderSpecialistSuggestionDto statusOrderSpecialistSuggestionDto);
    Boolean changeStatusOrderToDone(OrdersDto ordersDto);
    Boolean CheckTimeOfWork(SpecialistSuggestionDto specialistSuggestionDto);
    SpecialistSuggestion findById(Long id);



}
