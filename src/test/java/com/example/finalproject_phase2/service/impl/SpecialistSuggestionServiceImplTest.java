package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.dto.customerDto.CustomerDtoEmail;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersDtoWithOrdersStatus;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.*;
import com.example.finalproject_phase2.entity.SpecialistSuggestion;
import com.example.finalproject_phase2.entity.enumeration.OrderStatus;
import com.example.finalproject_phase2.entity.enumeration.SpecialistSelectionOfOrder;
import com.example.finalproject_phase2.service.*;
import com.example.finalproject_phase2.mapper.CustomerMapper;
import com.example.finalproject_phase2.mapper.OrdersMapper;
import com.example.finalproject_phase2.mapper.SpecialistSuggestionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpecialistSuggestionServiceImplTest {
    @Autowired
    SpecialistSuggestionService specialistSuggestionService;
    @Autowired
    OrdersService ordersService;
    @Autowired
    WalletService walletService;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    SpecialistService specialistService;
    @Autowired
     CustomerService customerService;
    @Autowired
    SpecialistSuggestionMapper specialistSuggestionMapper;
    @Autowired
    OrdersMapper ordersMapper;
    @BeforeEach
    void setUp() {

    }

    @Test
    void isValidSpecialSuggestion() {
        SuggestionDto validSpecialistSuggestionDto=new SuggestionDto();
        validSpecialistSuggestionDto.setSpecialistEmail("mohammad@gmail.com");
        validSpecialistSuggestionDto.setOrdersId(1l);
        validSpecialistSuggestionDto.setDateOfStartWork(LocalDate.now());
        validSpecialistSuggestionDto.setTimeOfStartWork(LocalTime.now());
        validSpecialistSuggestionDto.setProposedPrice(140d);
        validSpecialistSuggestionDto.setSubDutyName("CD");
        validSpecialistSuggestionDto.setWorkTimePerHour(10);
      assertEquals(true ,specialistSuggestionService.IsValidSpecialSuggestion(validSpecialistSuggestionDto));
    }
    @Test
    void inValidSpecialSuggestion() {
        SuggestionDto validSpecialistSuggestionDto=new SuggestionDto();
        validSpecialistSuggestionDto.setSpecialistEmail("mohammad@gmail.com");
        validSpecialistSuggestionDto.setOrdersId(1l);
        validSpecialistSuggestionDto.setDateOfStartWork(LocalDate.now());
        validSpecialistSuggestionDto.setTimeOfStartWork(LocalTime.now());
        validSpecialistSuggestionDto.setProposedPrice(140d);
        validSpecialistSuggestionDto.setSubDutyName("CD");
        validSpecialistSuggestionDto.setWorkTimePerHour(10);
        assertEquals("500" ,specialistSuggestionService.IsValidSpecialSuggestion(validSpecialistSuggestionDto));
    }
    @Test
    void findCustomerOrderSuggestionOnPrice(){
        CustomerDtoEmail customerDtoEmail=new CustomerDtoEmail("mahan@gmail.com");
         specialistSuggestionService.findCustomerOrderSuggestionOnPrice(customerDtoEmail).
  forEach(specialistSuggestion -> System.out.println(specialistSuggestion.getProposedPrice()));
    }
    @Test
    void findCustomerOrderSuggestionOnScoreOfSpecialist(){
        CustomerDtoEmail customerDtoEmail=new CustomerDtoEmail("mahan@gmail.com");
        List<SpecialistSuggestionResult> result = specialistSuggestionService.findCustomerOrderSuggestionOnPrice(customerDtoEmail);
assertEquals(5,result.get(0).getScore());
    }
    @Test
    void findSuggestWithThisSpecialistAndOrder() {
        SuggestionWithSpecialistAndOrdersDto specialistAndOrdersDto=new SuggestionWithSpecialistAndOrdersDto();
        specialistAndOrdersDto.setSpecialistEmail("ali@gmail.com");
        specialistAndOrdersDto.setOrderId(1l);
        assertTrue(specialistSuggestionService.findSuggestWithThisSpecialistAndOrder(specialistAndOrdersDto));

    }
    @Test
   void changeStatusOrderToWaitingForSpecialistToWorkplace(){

      assertEquals(true,specialistSuggestionService.changeStatusOrderToWaitingForSpecialistToWorkplace(ordersService.findById(1l).get(),specialistService.findByEmail("ali@gmail.com")));
   }
   @Test
    void changeSpecialistSelectedOfOrder(){
       SpecialistSuggestion specialistSuggestion = specialistSuggestionService.findById(2l);
       SuggestionStatusAndIdDto  suggestionStatusAndIdDto=new SuggestionStatusAndIdDto(SpecialistSelectionOfOrder.SELECTED,specialistSuggestion.getId());
       specialistSuggestionService.submitSpecialistSuggestion(specialistSuggestion);
   }
   @Test
    void changeStatusOrderToStarted(){
       SpecialistSuggestion specialistSuggestion = specialistSuggestionService.findById(2l);
       StatusOrderSpecialistSuggestionDto statusOrderSpecialistSuggestionDto=new StatusOrderSpecialistSuggestionDto();
       statusOrderSpecialistSuggestionDto.setSpecialistSuggestionId(specialistSuggestion.getId());
       statusOrderSpecialistSuggestionDto.setOrdersId(1l);
        assertEquals(true,specialistSuggestionService.changeStatusOrderToStarted(statusOrderSpecialistSuggestionDto));
   }
   @Test
    void changeStatusOrderToDone(){
       OrdersDtoWithOrdersStatus ordersDtoWithOrdersStatus=new OrdersDtoWithOrdersStatus();
       ordersDtoWithOrdersStatus.setOrdersId(1l);
       ordersDtoWithOrdersStatus.setOrderStatus(OrderStatus.ORDER_DONE);
       assertEquals(true,specialistSuggestionService.changeStatusOrderToDone(ordersDtoWithOrdersStatus));
   }
   @Test
    void CheckTimeOfWork(){
//        SpecialistSuggestionDto specialistSuggestionDto=new SpecialistSuggestionDto();
//        specialistSuggestionDto.setSpecialist(specialistService.findByEmail("ali@gmail.com"));
       SpecialistSuggestionDto specialistSuggestionDto = specialistSuggestionMapper.specialistSuggestionToSpecialistsuggestionDto(specialistSuggestionService.findById(2l));
       assertEquals(true,specialistSuggestionService.CheckTimeOfWork(specialistSuggestionDto));
   }
   @Test
    void payForSpecialistSuggestion(){
       // SpecialistSuggestionIdDto specialistSuggestionIdDto =new SpecialistSuggestionIdDto();
       SpecialistSuggestion specialistSuggestion = specialistSuggestionService.findById(2l);
       assertEquals("transaction is success",walletService.payWithWallet(specialistSuggestion));

   }
}