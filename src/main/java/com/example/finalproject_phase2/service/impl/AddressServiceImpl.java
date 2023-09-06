package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.addressDto.AddressDto;
import com.example.finalproject_phase2.entity.Address;
import com.example.finalproject_phase2.repository.AddressRepository;
import com.example.finalproject_phase2.service.AddressService;
import com.example.finalproject_phase2.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public Address createAddress(AddressDto addressDto) {
        return addressRepository.save(addressMapper.addressDtoToAddress(addressDto));
    }

    @Override
    public AddressDto removeAddress(AddressDto addressDto) {
        try {
            addressRepository.findAddressById(addressMapper.addressDtoToAddress(addressDto)).ifPresentOrElse(
                    addressCandidate -> {
                        addressRepository.delete(addressMapper.addressDtoToAddress(addressDto));
                    }
                    , () -> {
                        throw new CustomException("no any address found");
                    }
            );
        }catch (CustomException cnr){
            throw new CustomException(cnr.getMessage());
        }
        return addressDto;
    }


    public Address findById(Long id) {
        return addressRepository.findById(id).get();
    }

}
