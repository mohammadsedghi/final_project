package com.example.finalproject_phase2.mapper;

import com.example.finalproject_phase2.dto.customerCommentsDto.CustomerCommentsDto;
import com.example.finalproject_phase2.entity.CustomerComments;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerCommentsMapper {
      CustomerCommentsDto customerCommentsToCustomerCommentsDto(CustomerComments customerComments);


      CustomerComments customerCommentsDtoToCustomerComments(CustomerCommentsDto customerCommentsDto) ;

}
