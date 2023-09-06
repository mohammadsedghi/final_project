package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.custom_exception.CustomNumberFormatException;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerResult;
import com.example.finalproject_phase2.dto.dutyDto.DutyNameDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersAdvanceSearchParameter;
import com.example.finalproject_phase2.dto.ordersDto.OrdersResult;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistResult;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDtoDescription;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.securityConfig.AuthenticationResponse;
import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.dto.adminDto.AdminDto;
import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.dto.dutyDto.DutyDto;
import com.example.finalproject_phase2.service.*;
import com.example.finalproject_phase2.mapper.AdminMapper;
import com.example.finalproject_phase2.mapper.DutyMapper;
import com.example.finalproject_phase2.mapper.SubDutyMapper;
import com.example.finalproject_phase2.util.CheckValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private  final AdminService adminService;
    private final AdminMapper adminMapper;
    private final AuthenticationProvider authenticationProvider;
    private final DutyService dutyService;
    private final DutyMapper dutyMapper;
    private final SubDutyService subDutyService;
    private final SubDutyMapper subDutyMapper;
    private final SpecialistService specialistService;
    private final CustomerService customerService;
    private final OrdersService ordersService ;


@Autowired
    public AdminController(AdminService adminService, AdminMapper adminMapper, AuthenticationProvider authenticationProvider, DutyService dutyService, DutyMapper dutyMapper, SubDutyService subDutyService, SubDutyMapper subDutyMapper, SpecialistService specialistService, CustomerService customerService, OrdersService ordersService) {
        this.adminService = adminService;
        this.adminMapper = adminMapper;
    this.authenticationProvider = authenticationProvider;
    this.dutyService = dutyService;
    this.dutyMapper = dutyMapper;
    this.subDutyService = subDutyService;
    this.subDutyMapper = subDutyMapper;
    this.specialistService = specialistService;
    this.customerService = customerService;
    this.ordersService = ordersService;
}

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid AdminDto adminDto){
        System.out.println(adminMapper.adminDtoToAdmin(adminDto).getEmail());
        return  ResponseEntity.ok(adminService.register(adminMapper.adminDtoToAdmin(adminDto)));
    }
    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AdminLoginDto adminLoginDto
            , @RequestParam String userType){
        CheckValidation.userType=userType;
        System.out.println(userType);
        if (userType.equals("admin")){
            return  ResponseEntity.ok(adminService.authenticate(adminLoginDto));
        }else  return new ResponseEntity<>(new AuthenticationResponse(), HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/duty/submit")
    public ResponseEntity<DutyDto> addDuty(@RequestBody DutyDto dutyDto) {
        DutyDto dutyDtoGenerated = dutyService.addDuty(dutyDto);
        if (dutyDtoGenerated!=null)return new ResponseEntity<>(dutyDtoGenerated, HttpStatus.ACCEPTED);
        else throw new CustomException("duty not saved");
    }
    @GetMapping("/duty/findAll")
    public Set<DutyDto> getAllDuty() {
        return dutyService.findAllByDuties();
    }
    @PostMapping("/addSubDuty")
    public ResponseEntity<SubDutyDto> addSubDuty(@RequestBody SubDutyDto subDutyDto) {
        SubDutyDto subDutyDtoCandidate = subDutyService.addSubDuty(subDutyDto);
        if (subDutyDto!=null)return new ResponseEntity<>(subDutyDtoCandidate, HttpStatus.ACCEPTED);
        else throw  new CustomException("subDuty not saved");
    }
    @PostMapping("/editSubDutyPrice")
    public ResponseEntity<SubDutyDto> editSubDutyPrice(@RequestBody EditSubDutyDto editSubDutyDto) {
        SubDutyDto subDutyDto = subDutyService.editSubDutyPrice(editSubDutyDto);
        if (subDutyDto!=null)return new ResponseEntity<>(subDutyDto, HttpStatus.ACCEPTED);
        else throw  new CustomNumberFormatException("invalid price");
    }
    @PostMapping("/editSubDutyDescription")
    public ResponseEntity<SubDutyDto> editSubDutyDescription(@RequestBody EditSubDutyDtoDescription editSubDutyDtoDescription) {
        SubDutyDto subDutyDto = subDutyService.editSubDutyDescription(editSubDutyDtoDescription);
        if (subDutyDto!=null)return new ResponseEntity<>(subDutyDto, HttpStatus.ACCEPTED);
        else throw  new CustomNumberFormatException("invalid price");
    }
    @GetMapping("/findAllSubDuty")
    public Set<SubDutyDto> getAllSubDuty(DutyDto dutyDto) {
        return subDutyMapper.collectionOfSubDutyToSetOfSubDutyDto(subDutyService.showAllSubDutyOfDuty(dutyDto));
    }
    @PostMapping("/confirmByAdmin")
    public ResponseEntity<SpecialistDto> confirmSpecialistByAdmin(@RequestBody @Valid SpecialistDto specialistDto) {
        SpecialistDto specialistDtoCandidate = specialistService.confirmSpecialistByAdmin(specialistDto);
        if (specialistDtoCandidate!=null)return new ResponseEntity<>(specialistDtoCandidate, HttpStatus.ACCEPTED);
        else throw new CustomException("confirm by admin have error");
    }
    @PostMapping("/searchSpecialist")
    public ResponseEntity<List<SpecialistResult>> searchSpecialist(@RequestBody SpecialistDto specialistDto) {
        List<SpecialistResult> specialists = specialistService.searchSpecialist(specialistDto);
        CheckValidation.memberTypespecialist= specialistService.findByEmail(specialists.get(0).getEmail());
        System.out.println(CheckValidation.memberTypespecialist.getEmail());
        return new ResponseEntity<>(specialists, HttpStatus.ACCEPTED);
    }
    @PostMapping("/searchCustomer")
    public ResponseEntity<List<CustomerResult>> searchCustomer(@RequestBody CustomerDto customerDto) {
        List<CustomerResult> customers = customerService.searchCustomer(customerDto);
        return new ResponseEntity<>(customers, HttpStatus.ACCEPTED);
    }
    @PostMapping("/advanceSearch")
    public ResponseEntity<List<OrdersResult>> searchOrders(@RequestBody OrdersAdvanceSearchParameter ordersAdvanceSearchParameter ){
//        System.out.println(ordersAdvanceSearchParameter.getDateOfWork()+"dddddddd");
        List<OrdersResult> ordersResults = ordersService.searchInDuty(ordersAdvanceSearchParameter);
        if (ordersResults.size()==0){
            throw new CustomException("with this parameter not found any things");
        }else {
            return new ResponseEntity<>(ordersService.searchInDuty(ordersAdvanceSearchParameter), HttpStatus.ACCEPTED);
        }
    }
}
