package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.dto.ordersDto.OrdersDto;
import com.example.finalproject_phase2.dto.specialistSuggestionDto.*;
import com.example.finalproject_phase2.entity.SpecialistSuggestion;
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
        ValidSpecialistSuggestionDto validSpecialistSuggestionDto=new ValidSpecialistSuggestionDto();
        validSpecialistSuggestionDto.setSpecialist(specialistService.findByEmail("mohammad@gmail.com"));
        validSpecialistSuggestionDto.setOrders( ordersService.findById(1l).get());
        validSpecialistSuggestionDto.setDay(12);
        validSpecialistSuggestionDto.setMonth(9);
        validSpecialistSuggestionDto.setYear(2023);
        validSpecialistSuggestionDto.setHour(13);
        validSpecialistSuggestionDto.setMinutes(20);
        validSpecialistSuggestionDto.setProposedPrice(140d);
        validSpecialistSuggestionDto.setSubDuty(ordersService.findById(1l).get().getSubDuty());
        validSpecialistSuggestionDto.setWorkTimePerHour(10);
      assertEquals(true ,specialistSuggestionService.IsValidSpecialSuggestion(validSpecialistSuggestionDto));
    }
    @Test
    void inValidSpecialSuggestion() {
        ValidSpecialistSuggestionDto validSpecialistSuggestionDto=new ValidSpecialistSuggestionDto();
        validSpecialistSuggestionDto.setSpecialist(specialistService.findByEmail("mohammad@gmail.com"));
        validSpecialistSuggestionDto.setOrders( ordersService.findById(1l).get());
        validSpecialistSuggestionDto.setDay(12);
        validSpecialistSuggestionDto.setMonth(9);
        validSpecialistSuggestionDto.setYear(2023);
        validSpecialistSuggestionDto.setHour(13);
        validSpecialistSuggestionDto.setMinutes(20);
        validSpecialistSuggestionDto.setProposedPrice(140d);
        validSpecialistSuggestionDto.setSubDuty(ordersService.findById(1l).get().getSubDuty());
        validSpecialistSuggestionDto.setWorkTimePerHour(10);
        assertEquals("500" ,specialistSuggestionService.IsValidSpecialSuggestion(validSpecialistSuggestionDto));
    }
    @Test
    void findCustomerOrderSuggestionOnPrice(){

         specialistSuggestionService.findCustomerOrderSuggestionOnPrice
                 (customerMapper.customerToCustomerDto(customerService.findByEmail("mahan@gmail.com").get())).
                 forEach(specialistSuggestion -> System.out.println(specialistSuggestion.getProposedPrice()));
    }
    @Test
    void findCustomerOrderSuggestionOnScoreOfSpecialist(){

        specialistSuggestionService.findCustomerOrderSuggestionOnPrice
                        (customerMapper.customerToCustomerDto(customerService.findByEmail("mahan@gmail.com").get())).
                forEach(specialistSuggestion -> System.out.println(specialistSuggestion.getSpecialist().getScore()));
    }
    @Test
    void findSuggestWithThisSpecialistAndOrder() {
        StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist=new
                StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist();
        statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist.setSpecialist(specialistService.findByEmail("ali@gmail.com"));
        statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist.setOrders( ordersService.findById(1l).get());
        assertTrue(specialistSuggestionService.findSuggestWithThisSpecialistAndOrder(statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist));

    }
    @Test
   void changeStatusOrderToWaitingForSpecialistToWorkplace(){
        StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist=new
                StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist();
        statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist.setSpecialist( specialistService.findByEmail("ali@gmail.com"));
        statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist.setOrders(ordersService.findById(1l).get());
      assertEquals(true,specialistSuggestionService.changeStatusOrderToWaitingForSpecialistToWorkplace(statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist));
   }
   @Test
    void changeSpecialistSelectedOfOrder(){
       SpecialistSuggestion specialistSuggestion = specialistSuggestionService.findById(2l);
       specialistSuggestion.setSpecialistSelectionOfOrder( specialistSuggestionService.changeSpecialistSelectedOfOrder(SpecialistSelectionOfOrder.SELECTED));
        specialistSuggestionService.submitSpecialistSuggestion(specialistSuggestion);
   }
   @Test
    void changeStatusOrderToStarted(){
       SpecialistSuggestion specialistSuggestion = specialistSuggestionService.findById(2l);
       StatusOrderSpecialistSuggestionDto statusOrderSpecialistSuggestionDto=new StatusOrderSpecialistSuggestionDto();
       statusOrderSpecialistSuggestionDto.setSpecialistSuggestion(specialistSuggestion);
       statusOrderSpecialistSuggestionDto.setOrders(ordersService.findById(1l).get());
        assertEquals(true,specialistSuggestionService.changeStatusOrderToStarted(statusOrderSpecialistSuggestionDto));
   }
   @Test
    void changeStatusOrderToDone(){
       OrdersDto ordersDto= ordersMapper.ordersToOrdersDto(ordersService.findById(1l).get());
       assertEquals(true,specialistSuggestionService.changeStatusOrderToDone(ordersDto));
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