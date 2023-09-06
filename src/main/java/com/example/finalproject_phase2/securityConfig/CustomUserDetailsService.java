package com.example.finalproject_phase2.securityConfig;

import com.example.finalproject_phase2.entity.Admin;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.repository.AdminRepository;
import com.example.finalproject_phase2.repository.CustomerRepository;
import com.example.finalproject_phase2.repository.SpecialistRepository;
import com.example.finalproject_phase2.util.CheckValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final SpecialistRepository specialistRepository;
    private final CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (CheckValidation.userType.equals("admin")) {
            Optional<Admin> userOptional = adminRepository.findByEmail(username.trim());
            if (userOptional.isPresent()) {
                return new AdminUserDetail(
                        userOptional.get()
                );
            }
            throw new UsernameNotFoundException(username + " not found");
        }
    else if(CheckValidation.userType.equals("customer")){
        Optional<Customer> userOptional = customerRepository.findByEmail(username.trim());
        if (userOptional.isPresent()) {
            return new CustomerUserDetail(
                    userOptional.get()
            );
        }
        throw new UsernameNotFoundException(username + " not found");
    }else {
            Optional<Specialist> userOptional = specialistRepository.findByEmail(username.trim());
            if (userOptional.isPresent()) {
                return new SpecialistUserDetail(
                        userOptional.get()
                );
            }
            throw new UsernameNotFoundException(username + " not found");
        }
    }
}
