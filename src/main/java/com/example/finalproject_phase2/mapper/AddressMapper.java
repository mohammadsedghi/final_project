package com.example.finalproject_phase2.mapper;

import com.example.finalproject_phase2.dto.addressDto.AddressDto;
import com.example.finalproject_phase2.entity.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {
     Address addressDtoToAddress(AddressDto addressDto);
      AddressDto addressToAddressDto(Address address);


}
