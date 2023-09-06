package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.customerCommentsDto.CustomerCommentsDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistScoreDto;
import com.example.finalproject_phase2.entity.CustomerComments;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.repository.CustomerCommentsRepository;
import com.example.finalproject_phase2.service.CustomerCommentsService;
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
    @Autowired
    public CustomerCommentsServiceImpl(CustomerCommentsRepository customerCommentsRepository, SpecialistService specialistService, CustomerCommentsMapper customerCommentsMapper) {
        this.customerCommentsRepository = customerCommentsRepository;
        this.specialistService = specialistService;
        this.customerCommentsMapper = customerCommentsMapper;
    }
CheckValidation checkValidation=new CheckValidation();
    @Override
    public Boolean submitCustomerCommentsService(CustomerCommentsDto customerCommentsDto) {
        try {
          if (!checkValidation.isValid(customerCommentsDto)) throw new CustomException("invalid customerComments");
        }catch (CustomException ce){
            throw new CustomException("invalid customerComments");
        }
        customerCommentsRepository.save(customerCommentsMapper.customerCommentsDtoToCustomerComments(customerCommentsDto));
        SpecialistScoreDto specialistScoreDto=new SpecialistScoreDto();
        Specialist specialist=customerCommentsMapper.customerCommentsDtoToCustomerComments(customerCommentsDto).getOrders().getSpecialist();
        specialistScoreDto.setSpecialist(specialist);
        Integer number = findNumberOFCustomerCommentsThatSpecialistIsExist(specialist);
        Integer finalScore=(((number*specialist.getScore())+customerCommentsDto.getScore())/number+1);
        specialistScoreDto.setScore(finalScore);
        specialistService.updateSpecialistScore(specialistScoreDto);
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
