package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDtoEmail;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDtoWithOrdersStatus;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.*;
import com.example.finalproject_phase2.entity.*;
import com.example.finalproject_phase2.entity.enumeration.SpecialistSelectionOfOrder;

import java.util.List;
import java.util.Optional;

public interface SpecialistSuggestionService {
    Boolean submitSpecialistSuggestion(SpecialistSuggestion specialistSuggestion);
    Boolean findSuggestWithThisSpecialistAndOrder( SuggestionWithSpecialistAndOrdersDto specialistAndOrdersDto);
    Boolean IsValidSpecialSuggestion(SuggestionDto suggestionDto);
    List<SpecialistSuggestionResult> findCustomerOrderSuggestionOnPrice(CustomerDtoEmail customerDtoEmail);
    List<SpecialistSuggestionResult> findCustomerOrderSuggestionOnScoreOfSpecialist(CustomerDtoEmail customerDtoEmail);
    Boolean changeStatusOrderToWaitingForSpecialistToWorkplace( Orders order,Specialist specialist);
    SpecialistSelectionOfOrder changeSpecialistSelectedOfOrder( SuggestionStatusAndIdDto suggestionStatusAndIdDto);
    Boolean changeStatusOrderToStarted(StatusOrderSpecialistSuggestionDto statusOrderSpecialistSuggestionDto);
    Boolean changeStatusOrderToDone(OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus);
    Boolean CheckTimeOfWork(SpecialistSuggestionDto specialistSuggestionDto);
    SpecialistSuggestion findById(Long id);



}
