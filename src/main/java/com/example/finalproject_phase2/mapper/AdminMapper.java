package com.example.finalproject_phase2.mapper;

import com.example.finalproject_phase2.dto.adminDto.AdminDto;
import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.entity.Admin;
import org.mapstruct.Mapper;

@Mapper
public interface AdminMapper {
   Admin adminLoginDtoToAdmin(AdminLoginDto adminLoginDto);
    AdminDto adminToAdminDto(Admin admin);
    Admin adminDtoToAdmin(AdminDto adminDto);

}
