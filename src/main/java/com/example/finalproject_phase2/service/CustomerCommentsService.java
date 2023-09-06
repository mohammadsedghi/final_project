package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.customerCommentsDto.CustomerCommentsDto;
import com.example.finalproject_phase2.entity.CustomerComments;
import com.example.finalproject_phase2.entity.Specialist;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerCommentsService {
    Boolean submitCustomerCommentsService(CustomerCommentsDto customerCommentsDto);
    Integer findNumberOFCustomerCommentsThatSpecialistIsExist(Specialist specialist);
    Optional<CustomerComments> findById(Long id);
    List<CustomerComments> findCustomerCommentsByThisSpecialistIsExist(Specialist specialist);
    Integer showScoreOfLastCustomerCommentsThatThisSpecialistIsExist(Specialist specialist);

}
