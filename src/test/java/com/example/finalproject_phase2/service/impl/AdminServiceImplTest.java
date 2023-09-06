package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.service.AdminService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminServiceImplTest {
    @Autowired
    AdminService adminService;
    MotherObject motherObject;
    AdminLoginDto adminLoginDto;

    @BeforeEach
    void setUp() {
        motherObject = new MotherObject();
        adminLoginDto=new AdminLoginDto();
    }

    @Test
    @Order(1)
    void loginByEmailAndPassword() {
        assertEquals(motherObject.getValidAdminLoginDto(), adminService.loginByEmailAndPassword(motherObject.getValidAdminLoginDto()));

    }

    @Test
    @Order(2)
    void encryptAdminPassword() {
        String hashPassword = "m2PX/usxKeK789LK2SR5G2LHeYMUZOCDdoL55YfPnpLkrHuQj1h8DuDJG9PZyTybf/WpQh6slHQywBuaOcsyJw==";
        assertEquals(hashPassword, adminService.encryptAdminPassword(motherObject.getAdminValidPassword()));

    }
}