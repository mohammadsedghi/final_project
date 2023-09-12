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
        StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist=new
                StatusOrderSpecialistSuggestionDtoWithOrderAndSpecialist();
        statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist.setSpecialist(specialistService.findByEmail("ali@gmail.com"));
        statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist.setOrders( ordersService.findById(1l).get());
        assertTrue(specialistSuggestionService.findSuggestWithThisSpecialistAndOrder(statusOrderSpecialistSuggestionDtoWithOrderAndSpecialist));

    }
    @Test
   void changeStatusOrderToWaitingForSpecialistToWorkplace(){

      assertEquals(true,specialistSuggestionService.changeStatusOrderToWaitingForSpecialistToWorkplace(ordersService.findById(1l).get(),specialistService.findByEmail("ali@gmail.com")));
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