package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.securityConfig.AuthenticationResponse;
import com.example.finalproject_phase2.securityConfig.CustomUserDetailsService;
import com.example.finalproject_phase2.securityConfig.JwtService;
import com.example.finalproject_phase2.custom_exception.CustomNoResultException;
import com.example.finalproject_phase2.dto.adminDto.AdminDto;
import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.entity.Admin;
import com.example.finalproject_phase2.repository.AdminRepository;
import com.example.finalproject_phase2.service.AdminService;
import com.example.finalproject_phase2.mapper.AdminMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import com.example.finalproject_phase2.util.hash_password.EncryptPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
   private final AdminRepository adminRepository;
   private final AdminMapper adminMapper;
   CheckValidation checkValidation=new CheckValidation();
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, AdminMapper adminMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public AdminDto loginByEmailAndPassword(AdminLoginDto adminLoginDto) {
        try {
            if (checkValidation.isValidEmail(adminLoginDto.getEmail()) && checkValidation.isValidPassword(adminLoginDto.getPassword())) {
             //   adminRepository.findByEmailAndPassword(adminLoginDto.getEmail(), encryptAdminPassword(adminLoginDto.getPassword())).ifPresentOrElse(
                adminRepository.findByEmailAndPassword(adminLoginDto.getEmail(), adminLoginDto.getPassword()).ifPresentOrElse(
                        admin -> {
                            CheckValidation.memberTypeAdmin = admin;
                        }
                        , () -> {
                            throw new CustomNoResultException("customer not found");
                        }
                );
            } else {
                throw new CustomNoResultException("you inter invalid input for login");
            }
        } catch (CustomNoResultException cre) {
            CheckValidation.memberTypeAdmin = new Admin();
            return new AdminDto();
        }
        return adminMapper.adminToAdminDto(CheckValidation.memberTypeAdmin) ;
    }

    @Override
    public String encryptAdminPassword(String password) {
        EncryptPassword encryptPassword = new EncryptPassword();
        return encryptPassword.hashPassword(password);
    }
    @Override
    public Optional<Admin> findByEmail(String email){
        return adminRepository.findByEmail(email);
    }
@Override
    public AuthenticationResponse register(Admin admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
      adminRepository.save(admin);
     adminMapper.adminToAdminDto(admin);
      String jwtToken=jwtService.generateToken(customUserDetailsService.loadUserByUsername(admin.getEmail()));
      return  AuthenticationResponse.builder()
              .token(jwtToken)
              .build();
    }
    @Override
    public AuthenticationResponse authenticate(AdminLoginDto  adminLoginDto){
  authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  adminLoginDto.getEmail(),adminLoginDto.getPassword()
          )
  );
  Admin admin=adminRepository.findByEmail(adminLoginDto.getEmail()).orElseThrow();
        String jwtToken=jwtService.generateToken(customUserDetailsService.loadUserByUsername(admin.getEmail()));
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }





}
