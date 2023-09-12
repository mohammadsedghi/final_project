package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.customerDto.CustomerDtoEmail;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDtoWithOrdersStatus;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistScoreDto;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.*;
import com.example.finalproject_phase2.entity.*;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import com.example.finalproject_phase2.entity.enumeration.SpecialistSelectionOfOrder;
import com.example.finalproject_phase2.repository.SpecialistSuggestionRepository;
import com.example.finalproject_phase2.service.OrdersService;
import com.example.finalproject_phase2.service.SpecialistService;
import com.example.finalproject_phase2.service.SpecialistSuggestionService;
import com.example.finalproject_phase2.mapper.CustomerMapper;
import com.example.finalproject_phase2.mapper.OrdersMapper;
import com.example.finalproject_phase2.mapper.SpecialistSuggestionMapper;
import com.example.finalproject_phase2.util.validation.CheckValidation;
import com.example.finalproject_phase2.util.validation.CalenderAndValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialistSuggestionServiceImpl implements SpecialistSuggestionService {
    private final SpecialistSuggestionRepository specialistSuggestionRepository;
    private final OrdersService ordersService;
    private final CustomerMapper customerMapper;
    private final SpecialistService specialistService;
    private final OrdersMapper ordersMapper;
    private final SpecialistSuggestionMapper specialistSuggestionMapper;
    CalenderAndValidation calenderAndValidation = new CalenderAndValidation();
    CheckValidation checkValidation = new CheckValidation();

    @Autowired
    public SpecialistSuggestionServiceImpl(SpecialistSuggestionRepository specialistSuggestionRepository, OrdersService ordersService, CustomerMapper customerMapper, SpecialistService specialistService, OrdersMapper ordersMapper, SpecialistSuggestionMapper specialistSuggestionMapper) {
        this.specialistSuggestionRepository = specialistSuggestionRepository;
        this.ordersService = ordersService;
        this.customerMapper = customerMapper;
        this.specialistService = specialistService;
        this.ordersMapper = ordersMapper;
        this.specialistSuggestionMapper = specialistSuggestionMapper;
    }

    @Override
    public Boolean IsValidSpecialSuggestion(ValidSpecialistSuggestionDto validSpecialistSuggestionDto){
        try {
            StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist=new StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist();
            statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist.setSpecialist(validSpecialistSuggestionDto.getSpecialist());
            statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist.setOrders(validSpecialistSuggestionDto.getOrders());
            if (!findSuggestWithThisSpecialistAndOrder(statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist)) {
                throw new CustomException("duplicate request for suggest");
            }
            if (!calenderAndValidation.setAndConvertDate(validSpecialistSuggestionDto.getOrders().getDateOfWork(),
                    validSpecialistSuggestionDto.getDay(), validSpecialistSuggestionDto.getMonth(), validSpecialistSuggestionDto.getYear(), validSpecialistSuggestionDto.getOrders().getTimeOfWork(), validSpecialistSuggestionDto.getHour(), validSpecialistSuggestionDto.getMinutes())) {
                throw new CustomException("order time of work is not valid");
            }
            OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus = new OrdersDtoWithOrdersStatus();
            ordersDtoWithOrdersStatus.setOrdersId(validSpecialistSuggestionDto.getOrders().getId());
            ordersDtoWithOrdersStatus.setOrderStatus(OrderStatus.ORDER_WAITING_FOR_SPECIALIST_SELECTION);
            SpecialistSuggestion specialistSuggestion = SpecialistSuggestion.builder()
                    .specialist(validSpecialistSuggestionDto.getSpecialist())
                    .order(ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus))
                    .DateOfSuggestion(LocalDate.now())
                    .TimeOfSuggestion(LocalTime.now())
                    .TimeOfStartWork(LocalTime.of(validSpecialistSuggestionDto.getHour(), validSpecialistSuggestionDto.getMinutes(), 0))
                    .DateOfStartWork(LocalDate.of(validSpecialistSuggestionDto.getYear(), validSpecialistSuggestionDto.getMonth(), validSpecialistSuggestionDto.getDay()))
                    .durationOfWorkPerHour(validSpecialistSuggestionDto.getWorkTimePerHour())
                    .proposedPrice(validSpecialistSuggestionDto.getSubDuty().getBasePrice() + validSpecialistSuggestionDto.getProposedPrice())
                    .build();
            return submitSpecialistSuggestion(specialistSuggestion);
        } catch (CustomException ce) {
            throw new CustomException("submitSpecialistSuggestion is not occur");
        }
    }

    @Override
    public Boolean submitSpecialistSuggestion(SpecialistSuggestion specialistSuggestion) {
        specialistSuggestionRepository.save(specialistSuggestion);
        return true;
    }

    @Override
    public List<SpecialistSuggestionResult> findCustomerOrderSuggestionOnPrice(CustomerDtoEmail customerDtoEmail) {
        List<SpecialistSuggestionResult> results=new ArrayList<>();
        Collection<SpecialistSuggestionDto> specialistSuggestionDtoCollection = specialistSuggestionMapper.
                specialistSuggestionCollectionToSpecialistSuggestionCollectionDto(specialistSuggestionRepository.
                        findCustomerOrderSuggestionOnPrice(customerDtoEmail.getEmail()));
        specialistSuggestionDtoCollection.forEach(specialistSuggestionDto ->
        results.add(new SpecialistSuggestionResult(
                specialistSuggestionDto.getSpecialist().getFirstName(),
                specialistSuggestionDto.getSpecialist().getLastName(),
                specialistSuggestionDto.getSpecialist().getScore(),
                specialistSuggestionDto.getProposedPrice(),
                specialistSuggestionDto.getTimeOfStartWork(),
                specialistSuggestionDto.getDateOfStartWork(),
                specialistSuggestionDto.getDurationOfWorkPerHour())));
        return results;
    }
    @Override
    public List<SpecialistSuggestionResult> findCustomerOrderSuggestionOnScoreOfSpecialist(CustomerDtoEmail customerDtoEmail) {
       List<SpecialistSuggestionResult> results=new ArrayList<>();
        Collection<SpecialistSuggestionDto> specialistSuggestionDtoCollection = specialistSuggestionMapper.
                specialistSuggestionCollectionToSpecialistSuggestionCollectionDto( specialistSuggestionRepository.findCustomerOrderSuggestionOnScoreOfSpecialist(customerDtoEmail.getEmail()));
        specialistSuggestionDtoCollection.forEach(specialistSuggestionDto ->
            results.add(new SpecialistSuggestionResult(
                    specialistSuggestionDto.getSpecialist().getFirstName(),
                    specialistSuggestionDto.getSpecialist().getLastName(),
                    specialistSuggestionDto.getSpecialist().getScore(),
                    specialistSuggestionDto.getProposedPrice(),
                    specialistSuggestionDto.getTimeOfStartWork(),
                    specialistSuggestionDto.getDateOfStartWork(),
                    specialistSuggestionDto.getDurationOfWorkPerHour())));
        return results;
    }
    @Override
    public Boolean changeStatusOrderToWaitingForSpecialistToWorkplace(Orders order,Specialist specialist) {
        order.setSpecialist(specialist);
        OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus = new OrdersDtoWithOrdersStatus();
        ordersDtoWithOrdersStatus.setOrdersId(order.getId());
        ordersDtoWithOrdersStatus.setOrderStatus(OrderStatus.ORDER_WAITING_FOR_SPECIALIST_TO_WORKPLACE);
        ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
        return true;
    }

    @Override
    public SpecialistSelectionOfOrder changeSpecialistSelectedOfOrder( SuggestionStatusAndIdDto suggestionStatusAndIdDto) {
        SpecialistSuggestion suggestion = findById(suggestionStatusAndIdDto.getId());
        suggestion.setSpecialistSelectionOfOrder(suggestionStatusAndIdDto.getSpecialistSelectionOfOrder());
        specialistSuggestionRepository.save(suggestion);
        return suggestionStatusAndIdDto.getSpecialistSelectionOfOrder();
    }

    @Override
    public SpecialistSuggestion findById(Long id) {
        return specialistSuggestionRepository.findById(id).get();
    }

    @Override
    public Boolean changeStatusOrderToStarted( StatusOrderSpecialistSuggestionDto statusOrderSpecialistSuggestionDto) {
        try {
            SpecialistSuggestion specialistSuggestion = findById(statusOrderSpecialistSuggestionDto.getSpecialistSuggestionId());
            if (specialistSuggestion.getSpecialistSelectionOfOrder() == SpecialistSelectionOfOrder.SELECTED) {
                OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus = new OrdersDtoWithOrdersStatus();
                ordersDtoWithOrdersStatus.setOrdersId(statusOrderSpecialistSuggestionDto.getOrdersId());
                ordersDtoWithOrdersStatus.setOrderStatus( OrderStatus.ORDER_STARTED);
                ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
            } else {
                throw new CustomException("customer not permission change status of this" +
                        " order to started in this level");
            }
        } catch (CustomException ce) {
            throw new CustomException("customer not permission change status of this" +
                    " order to started in this level");
        }
        return true;
    }

    @Override
    public Boolean changeStatusOrderToDone(OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus) {
        try {
            Optional<Orders> order = ordersService.findById(ordersDtoWithOrdersStatus.getOrdersId());
           if (order.isPresent()){
            if (order.get().getOrderStatus() == OrderStatus.ORDER_STARTED) {
                ordersDtoWithOrdersStatus.setOrderStatus(OrderStatus.ORDER_DONE);
                ordersService.updateOrderToNextLevel(ordersDtoWithOrdersStatus);
            }else {
                throw new CustomException("customer not permission change status of this" +
                        " order to started in this level");
            }
           } else throw new CustomException("order with this id not found");
        } catch (CustomException ce) {
            throw new CustomException(ce.getMessage());
        }
        return true;

    }

    @Override
    public Boolean CheckTimeOfWork(SpecialistSuggestionDto specialistSuggestionDto) {
        try {
            LocalDate temporaryDate=LocalDate.now();
            LocalTime temporaryTime = LocalTime.now();
            if(temporaryDate.getDayOfMonth()>specialistSuggestionDto.getDateOfStartWork().getDayOfMonth()){
                System.out.println("timeComparison: ");
                Specialist specialist = specialistSuggestionDto.getSpecialist();
                SpecialistScoreDto specialistScoreDto = new SpecialistScoreDto();
                specialistScoreDto.setSpecialist(specialist);
                System.out.println("sssss"+specialist.getScore());
                int newScore=((specialist.getScore())-24);
                specialistScoreDto.setScore(newScore);
                specialistService.updateSpecialistScore(specialistScoreDto);
                return true;
            }
            int timeComparison = temporaryTime.compareTo(specialistSuggestionDto.getTimeOfStartWork());

            if (timeComparison > specialistSuggestionDto.getDurationOfWorkPerHour()) {
                System.out.println("teeeeeeest");
                int timeError = timeComparison / specialistSuggestionDto.getDurationOfWorkPerHour();
                Specialist specialist = specialistSuggestionDto.getSpecialist();
                SpecialistScoreDto specialistScoreDto = new SpecialistScoreDto();
                specialistScoreDto.setSpecialist(specialist);
                specialistScoreDto.setScore(specialist.getScore() - timeError);
                specialistService.updateSpecialistScore(specialistScoreDto);
                return true;
            }
        }catch (CustomException ce){
            throw new CustomException("score is not updated");
        }
        return false;
    }


    @Override
    public Boolean findSuggestWithThisSpecialistAndOrder(StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist) {
        return specialistSuggestionRepository.findSuggestWithThisSpecialistAndOrder(statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist.getSpecialist(),
                statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist.getOrders()).isEmpty();
    }


}