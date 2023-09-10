package com.example.finalproject_phase2.securityConfig;

import com.example.finalproject_phase2.service.AdminService;
import com.example.finalproject_phase2.service.CustomerService;
import com.example.finalproject_phase2.service.SpecialistService;
import com.example.finalproject_phase2.util.CheckValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//@RequiredArgsConstructor
public class ApplicationConfig{

    private final AdminService adminService;
    private final CustomerService customerService;
   private final SpecialistService specialistService;
  // private final AdminRepository adminRepository;
  // private final SpecialistRepository specialistRepository;
  // private final CustomerRepository customerRepository;
private final CustomUserDetailsService customUserDetailsService;

    public ApplicationConfig(@Lazy AdminService adminService, @Lazy CustomerService customerService, @Lazy SpecialistService specialistService, CustomUserDetailsService customUserDetailsService) {
        this.adminService = adminService;
        this.customerService = customerService;
        this.specialistService = specialistService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        if (CheckValidation.userType.equals("admin")) {
           // return username -> (UserDetails) adminRepository.findByEmail(username)
            return username -> (UserDetails) adminService.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        } else if (CheckValidation.userType.equals("customer")) {
          //  return username -> (UserDetails) customerRepository.findByEmail(username)
            return username -> (UserDetails) customerService.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("user not found"));
      //  }else  return username -> (UserDetails) specialistRepository.findByEmail(username)
        }else  return username -> (UserDetails) specialistService.findByEmailOptional(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
