package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.dto.customerDto.CustomerResult;
import com.example.finalproject_phase2.securityConfig.AuthenticationResponse;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    boolean changePassword(String email,String password );
    List<CustomerResult> searchCustomer(CustomerDto customerDto);
    AuthenticationResponse register(Customer customer);
    AuthenticationResponse authenticate(CustomerLoginDto customerLoginDto);
    boolean isAccountActivated(String token);
    Optional<Customer> findByEmail(String email);
}
