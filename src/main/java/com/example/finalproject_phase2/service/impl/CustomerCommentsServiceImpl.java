package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.customerCommentsDto.CustomerCommentsDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistScoreDto;
import com.example.finalproject_phase2.entity.CustomerComments;
import com.example.finalproject_phase2.entity.Orders;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.repository.CustomerCommentsRepository;
import com.example.finalproject_phase2.service.CustomerCommentsService;
import com.example.finalproject_phase2.service.OrdersService;
import com.example.finalproject_phase2.service.SpecialistService;
import com.example.finalproject_phase2.mapper.CustomerCommentsMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerCommentsServiceImpl implements CustomerCommentsService {
    private final CustomerCommentsRepository customerCommentsRepository;
    private final SpecialistService specialistService;
    private final CustomerCommentsMapper customerCommentsMapper;
    private final OrdersService ordersService;
    @Autowired
    public CustomerCommentsServiceImpl(CustomerCommentsRepository customerCommentsRepository, SpecialistService specialistService, CustomerCommentsMapper customerCommentsMapper, OrdersService ordersService) {
        this.customerCommentsRepository = customerCommentsRepository;
        this.specialistService = specialistService;
        this.customerCommentsMapper = customerCommentsMapper;
        this.ordersService = ordersService;
    }
CheckValidation checkValidation=new CheckValidation();
    @Override
    public Boolean submitCustomerCommentsService(CustomerCommentsDto customerCommentsDto) {
        try {
            if (!checkValidation.isValid(customerCommentsDto)) throw new CustomException("invalid customerComments");

        Optional<Orders> order = ordersService.findById(customerCommentsDto.getOrdersId());
        if (order.isPresent()) {
            CustomerComments customerComments = new CustomerComments(order.get(), customerCommentsDto.getDescription(),
                    customerCommentsDto.getScore());
            customerCommentsRepository.save(customerComments);
            SpecialistScoreDto specialistScoreDto = new SpecialistScoreDto();
            Specialist specialist =customerComments.getOrders().getSpecialist();
            specialistScoreDto.setSpecialist(specialist);
            Integer number = findNumberOFCustomerCommentsThatSpecialistIsExist(specialist);
            int midResult=number * specialist.getScore();
            int finalNumber=number+1;
            Integer finalScore = (( midResult+ customerCommentsDto.getScore()) / finalNumber);
            System.out.println(customerComments.getScore());
            System.out.println(specialist.getScore());
            System.out.println(number);
            specialistScoreDto.setScore(finalScore);
            specialistService.updateSpecialistScore(specialistScoreDto);
        }
    }catch (CustomException ce){
            throw new CustomException(ce.getMessage());
        }
  return true;
    }

    @Override
    public Integer findNumberOFCustomerCommentsThatSpecialistIsExist(Specialist specialist ) {
        return customerCommentsRepository.findNumberOFCustomerCommentsThatSpecialistIsExist(specialist);
    }

    @Override
    public Optional<CustomerComments> findById(Long id) {
        return customerCommentsRepository.findById(id);
    }

    @Override
    public List<CustomerComments> findCustomerCommentsByThisSpecialistIsExist(Specialist specialist) {
        return customerCommentsRepository.findCustomerCommentsByThisSpecialistIsExist(specialist);
    }

    @Override
    public Integer showScoreOfLastCustomerCommentsThatThisSpecialistIsExist(Specialist specialist) {
        List<CustomerComments> customerCommentsList = findCustomerCommentsByThisSpecialistIsExist(specialist);
        LocalDateTime now = LocalDateTime.now();
        CustomerComments customerComments = customerCommentsList.stream()
                .min(Comparator.comparingInt(order ->
                        Math.toIntExact(ChronoUnit.SECONDS.between(order.getOrders().getDateOfWork(), now)))).orElse(null);
        return customerComments.getScore();
    }
}
