package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerResult;
import com.example.finalproject_phase2.dto.customerDto.CustomerSearchDto;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.service.email.EmailRequest;
import com.example.finalproject_phase2.mapper.CustomerMapper;
import com.example.finalproject_phase2.repository.CustomerRepository;
import com.example.finalproject_phase2.securityConfig.AuthenticationResponse;
import com.example.finalproject_phase2.securityConfig.CustomUserDetailsService;
import com.example.finalproject_phase2.securityConfig.JwtService;
import com.example.finalproject_phase2.service.CustomerService;
import com.example.finalproject_phase2.service.WalletService;
import com.example.finalproject_phase2.service.email.MailService;
import com.example.finalproject_phase2.util.CheckValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final WalletService walletService;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final MailService mailService;
    private String token;


    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, WalletService walletService, CustomerMapper customerMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, CustomUserDetailsService customUserDetailsService, MailService mailService) {
        this.customerRepository = customerRepository;
        this.walletService = walletService;
        this.customerMapper = customerMapper;

        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
        this.mailService = mailService;

    }

    public AuthenticationResponse register(Customer customer) {
        EmailRequest emailRequest = new EmailRequest();
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRegisterDate(LocalDate.now());
        customer.setRegisterTime(LocalTime.now());
        customer.setWallet(walletService.createWallet());
        customer.setIsEnable(false);
        customerRepository.save(customer);
        CheckValidation.memberTypeCustomer = customer;
        String jwtToken = jwtService.generateToken(customUserDetailsService.loadUserByUsername(customer.getEmail()));
        this.token = jwtToken;
        emailRequest.setTo(customer.getEmail());
        emailRequest.setSubject("activate account");
        emailRequest.setText("Click the following link to activate your account: http://localhost:8080/api/customer/activate?token=" + jwtToken);
        mailService.sendEmail(emailRequest.getTo(),emailRequest.getSubject(), emailRequest.getText());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(CustomerLoginDto customerLoginDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        customerLoginDto.getEmail(), customerLoginDto.getPassword()
                )
        );
        Customer customer = customerRepository.findByEmail(customerLoginDto.getEmail()).orElseThrow();
        CheckValidation.memberTypeCustomer = customer;
        String jwtToken = jwtService.generateToken(customUserDetailsService.loadUserByUsername(customer.getEmail()));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    @Override
    public boolean isAccountActivated(String token) {
        System.out.println("this token" + this.token);
        if (token.equals(this.token)) {
            Optional<Customer> customer = findByEmail(CheckValidation.memberTypeCustomer.getEmail());
            customer.get().setIsEnable(true);
            CheckValidation.memberTypeCustomer.setIsEnable(true);
            customerRepository.save(customer.get());
            return true;
        }
        return false;
    }

    public Specification<Customer> hasCustomerWithThisEmail(String email) {
        return (customer, cq, cb) -> cb.like(customer.get("email"), "%" + email + "%");
    }

    public Specification<Customer> hasCustomerWithThisFirstName(String firstName) {
        return (customer, cq, cb) -> cb.like(customer.get("firstName"), "%" + firstName + "%");
    }

    public Specification<Customer> hasCustomerWithThisLastName(String lastName) {
        return (customer, cq, cb) -> cb.like(customer.get("lastName"), "%" + lastName + "%");
    }

    public Specification<Customer> hasCustomerWithThisNationalId(String nationalId) {
        return (customer, cq, cb) -> cb.like(customer.get("nationalId"), "%" + nationalId + "%");
    }

    public Specification<Customer> hasCustomerSubmitBeforeThisTime(LocalTime registerTime) {
        return (customer, cq, cb) ->
                cb.lessThanOrEqualTo(customer.get("registerTime"), registerTime);
    }

    @Override
    public List<CustomerResult> searchCustomer(CustomerSearchDto customerSearchDto) {
        Customer searchCustomer = customerMapper.customerSearchDtoToCustomer(customerSearchDto);
        List<CustomerResult> customerList = new ArrayList<>();
        customerRepository.findAll(where(hasCustomerWithThisEmail(searchCustomer.getEmail())).
                and(hasCustomerWithThisFirstName(searchCustomer.getFirstName())).
                and(hasCustomerWithThisLastName(searchCustomer.getLastName()))
                .and(hasCustomerWithThisNationalId(searchCustomer.getNationalId()))
                .and(hasCustomerSubmitBeforeThisTime(searchCustomer.getRegisterTime()))
        ).forEach(customer -> customerList.add(new CustomerResult(customer.getFirstName(), customer.getLastName(), customer.getEmail())));

        return customerList;
    }



    @Override
    public boolean changePassword(String email, String newPassword) {
        Customer user = customerRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("user not found"));
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        customerRepository.save(user);
        return true;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return customerRepository.findByEmail(email);
        } catch (CustomException e) {
            throw new CustomException("email find have error");
        }
    }
}