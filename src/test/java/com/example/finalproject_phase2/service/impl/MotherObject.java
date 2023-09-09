package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.addressDto.AddressDto;
import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerChangePasswordDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerLoginDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.entity.Duty;
import com.example.finalproject_phase2.entity.SubDuty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@FieldDefaults (level = AccessLevel.PRIVATE)
public class MotherObject {
    String invalidEmail="mohammadgmail.com";
    String duplicateEmail="mahan@gmail.com";
    String adminValidPassword="123456ad";
    ProjectResponse projectResponseOfAddressRemoved=new ProjectResponse("202","address removed");


    public CustomerDto getValidCustomerSignUpDto(){
        return CustomerDto.builder()
                .firstName("mahan")
                .lastName("se")
                .nationalId("4560116814")
                .email("mahan@gmail.com")
                .password("123456mn")
                .build();
    }
    public CustomerDto getInValidCustomerSignUpDto(){
        return CustomerDto.builder()
                .firstName("mahan")
                .lastName("se")
                .nationalId("4560116814")
                .email("mahangmail.com")
                .password("123456mn")
                .build();
    }
    public CustomerLoginDto getValidCustomerLoginDto(){
        return CustomerLoginDto.builder()
                .email("mahan@gmail.com")
                .password("123456mn")
                .build();
    }
    public AdminLoginDto getValidAdminLoginDto(){
        return AdminLoginDto.builder()
                .email("mohammadsedghi1993@gmail.com")
                .password("123456ad")
                .build();
    }
    public CustomerLoginDto getValidCustomerLoginDtoAfterChangePassword(){
        return CustomerLoginDto.builder()
                .email("mahan@gmail.com")
                .password("123456nm")
                .build();
    }
    public CustomerLoginDto getInValidCustomerLoginDto(){
        return CustomerLoginDto.builder()
                .email("mahan@gmail.com")
                .password("123456")
                .build();
    }
    public CustomerChangePasswordDto getValidCustomerChangePasswordDto(){
        return CustomerChangePasswordDto.builder()
                .email("mahan@gmail.com")
                .oldPassword("123456mn")
                .newPassword("123456nm")
                .build();
    }
    public DutyDto getValidDutyDto(){
        return DutyDto.builder()
                .name("AAA")
                .subDuties(new HashSet<>())
                .build();
    }
    public Set<DutyDto> setOfDuty(){
        DutyDto dutyA=new DutyDto("AAA",new HashSet<>());
      //  DutyDto dutyB=new DutyDto("BBB",new HashSet<>());
        Set<DutyDto> duties=new HashSet<>();
        duties.add(dutyA);
      //  duties.add(dutyB);
        return duties;
    }
    public AddressDto getValidAddressDto(){
        return AddressDto.builder()
        .province("tehran")
                .city("tehran")
                        .street("azadi")
                                .postalCode("3514734473")
                                        .houseNumber(32)
                .build();
    }
    public SubDutyDto getValidSubDutyDto(){
        Duty duty=new Duty("AAA",new HashSet<>());
        duty.setId(1l);
        return SubDutyDto.builder()
                .dutyName(duty.getName())
                .basePrice(12d)
                .description("ABC")
                .name("AB")
                .build();
    }
    public EditSubDutyDto getValidEditSubDutyInfo(SubDuty subDuty){
        return EditSubDutyDto.builder().
                subDutyName(subDuty.getName())
              .basePrice("500").
                build();
    }
}
