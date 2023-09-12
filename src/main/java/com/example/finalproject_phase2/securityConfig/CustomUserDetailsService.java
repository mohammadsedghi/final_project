package com.example.finalproject_phase2.securityConfig;

import com.example.finalproject_phase2.entity.Admin;
import com.example.finalproject_phase2.entity.Customer;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.service.AdminService;
import com.example.finalproject_phase2.service.CustomerService;
import com.example.finalproject_phase2.service.SpecialistService;
import com.example.finalproject_phase2.util.CheckValidation;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
//@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  //  private final AdminRepository adminRepository;
  //  private final SpecialistRepository specialistRepository;
  // private final CustomerRepository customerRepository;
  private final SpecialistService specialistService;
    private final AdminService adminService;
    private final CustomerService customerService;

    public CustomUserDetailsService(@Lazy SpecialistService specialistService, @Lazy AdminService adminService, @Lazy CustomerService customerService) {
        this.specialistService = specialistService;
        this.adminService = adminService;
        this.customerService = customerService;
    }

    @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      if (CheckValidation.userType.equals("admin")) {
          // Optional<Admin> userOptional = adminRepository.findByEmail(username.trim());
          Optional<Admin> userOptional = adminService.findByEmail(username.trim());
          if (userOptional.isPresent()) {
              return new AdminUserDetail(
                      userOptional.get()
              );
          }
          throw new UsernameNotFoundException(username + " not found");
      }
      else if(CheckValidation.userType.equals("customer")){
          //  Optional<Customer> userOptional = customerRepository.findByEmail(username.trim());
          Optional<Customer> userOptional = customerService.findByEmail(username.trim());
          if (userOptional.isPresent()) {
              return new CustomerUserDetail(
                      userOptional.get()
              );
          }
          throw new UsernameNotFoundException(username + " not found");
      }else {
          //     Optional<Specialist> userOptional = specialistRepository.findByEmail(username.trim());
          Optional<Specialist> userOptional = specialistService.findByEmailOptional(username.trim());
          if (userOptional.isPresent()) {
              return new SpecialistUserDetail(
                      userOptional.get()
              );
          }
          throw new UsernameNotFoundException(username + " not found");
      }
  }

}
