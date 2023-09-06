package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.addressDto.AddressDto;
import com.example.finalproject_phase2.entity.Address;

public interface AddressService {
    Address createAddress(AddressDto addressDto);
    AddressDto removeAddress(AddressDto addressDto);

}
