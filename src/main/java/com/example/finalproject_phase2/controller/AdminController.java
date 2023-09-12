package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.custom_exception.CustomNumberFormatException;
import com.example.finalproject_phase2.dto.customerDto.CustomerDto;
import com.example.finalproject_phase2.dto.customerDto.CustomerDtoEmail;
import com.example.finalproject_phase2.dto.customerDto.CustomerResult;
import com.example.finalproject_phase2.dto.customerDto.CustomerSearchDto;
import com.example.finalproject_phase2.dto.dutyDto.DutyNameDto;
import com.example.finalproject_phase2.dto.ordersDto.OrdersAdvanceSearchParameter;
import com.example.finalproject_phase2.dto.ordersDto.OrdersResult;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistEmailDto;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistResult;
import com.example.finalproject_phase2.dto.specialistDto.SpecialistSearchDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.EditSubDutyDtoDescription;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyDto;
import com.example.finalproject_phase2.dto.subDutyDto.SubDutyNameDto;
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
import com.example.finalproject_phase2.util.validation.DtoValidation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
@Validated
public class AdminController {
    private final AdminService adminService;
    private final AdminMapper adminMapper;
    private final AuthenticationProvider authenticationProvider;
    private final DutyService dutyService;
    private final DutyMapper dutyMapper;
    private final SubDutyService subDutyService;
    private final SubDutyMapper subDutyMapper;
    private final SpecialistService specialistService;
    private final CustomerService customerService;
    private final OrdersService ordersService;
    DtoValidation dtoValidation = new DtoValidation();

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
    public ResponseEntity<AuthenticationResponse> register(@RequestBody  AdminDto adminDto) {
        System.out.println(adminMapper.adminDtoToAdmin(adminDto).getEmail());
        dtoValidation.isValid(adminDto);
        return ResponseEntity.ok(adminService.register(adminMapper.adminDtoToAdmin(adminDto)));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AdminLoginDto adminLoginDto
            , @RequestParam String userType) {
        CheckValidation.userType = userType;
        System.out.println(userType);
        dtoValidation.isValid(adminLoginDto);
        if (userType.equals("admin")) {
            return ResponseEntity.ok(adminService.authenticate(adminLoginDto));
        } else return new ResponseEntity<>(new AuthenticationResponse(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/duty/submit")
    public ResponseEntity<DutyDto> addDuty(@RequestBody DutyDto dutyDto) {
        dtoValidation.isValid(dutyDto);
        DutyDto dutyDtoGenerated = dutyService.addDuty(dutyMapper.dutyDtoToDuty(dutyDto));
        if (dutyDtoGenerated != null) return new ResponseEntity<>(dutyDtoGenerated, HttpStatus.ACCEPTED);
        else throw new CustomException("duty not saved");
    }

    @GetMapping("/duty/findAll")
    public Set<DutyNameDto> getAllDuty() {
        Set<DutyNameDto> dutyNameDtoSet = new HashSet<>();
        Set<DutyDto> allDuty = dutyService.findAllByDuties();
        for (DutyDto dutyDto : allDuty
        ) {
            dutyNameDtoSet.add(new DutyNameDto(dutyDto.getName()));
        }
        return dutyNameDtoSet;
    }

    @PostMapping("/addSubDuty")
    public ResponseEntity<SubDutyDto> addSubDuty(@RequestBody SubDutyDto subDutyDto) {
        dtoValidation.isValid(subDutyDto);
        SubDutyDto subDutyDtoCandidate = subDutyService.addSubDuty(subDutyDto);
      return new ResponseEntity<>(subDutyDtoCandidate, HttpStatus.ACCEPTED);
    }

    @PostMapping("/subDuty/editSubDutyPrice")
    public ResponseEntity<EditSubDutyDto> editSubDutyPrice(@RequestBody EditSubDutyDto editSubDutyDto) {
      dtoValidation.isValid(editSubDutyDto);
        EditSubDutyDto editSubDutyDtoResult = subDutyService.editSubDutyPrice(editSubDutyDto);
        return new ResponseEntity<>(editSubDutyDtoResult, HttpStatus.ACCEPTED);

    }

    @PostMapping("/subDuty/editSubDutyDescription")
    public ResponseEntity<EditSubDutyDtoDescription> editSubDutyDescription(@RequestBody EditSubDutyDtoDescription editSubDutyDtoDescription) {
        dtoValidation.isValid(editSubDutyDtoDescription);
        EditSubDutyDtoDescription editSubDutyDtoDescriptionResult = subDutyService.editSubDutyDescription(editSubDutyDtoDescription);
        return new ResponseEntity<>(editSubDutyDtoDescriptionResult, HttpStatus.ACCEPTED);

    }

    @PostMapping("/subDuty/findAll")
    public Set<SubDutyNameDto> AllSubDuty(@RequestBody DutyNameDto dutyNameDto) {
        dtoValidation.isValid(dutyNameDto);
       return  subDutyService.showAllSubDutyOfDuty(dutyNameDto);

    }

    @PostMapping("/confirmByAdmin")
    public ResponseEntity<SpecialistEmailDto> confirmSpecialistByAdmin(@RequestBody SpecialistEmailDto specialistEmailDto) {
        dtoValidation.isValid(specialistEmailDto);
      return new ResponseEntity<>(specialistService.confirmSpecialistByAdmin(specialistEmailDto), HttpStatus.ACCEPTED);
    }

    @PostMapping("/searchSpecialist")
    public ResponseEntity<Map<SpecialistResult,Long>> searchSpecialist(@RequestBody SpecialistSearchDto  specialistSearchDto) {
       if (specialistSearchDto.getRegisterTime()!=null) {
           specialistSearchDto.setRegisterTime(
               LocalTime.of(specialistSearchDto.getRegisterTime().getHour(),
                       specialistSearchDto.getRegisterTime().getMinute(),
                       specialistSearchDto.getRegisterTime().getSecond()+1));
       }
        if (specialistSearchDto.getRegisterTime() == null) specialistSearchDto.setRegisterTime(LocalTime.of(23,59));
        System.out.println(specialistSearchDto.getRegisterTime());
        List<SpecialistResult> specialists = specialistService.searchSpecialist(specialistSearchDto);
      //  CheckValidation.memberTypespecialist = specialistService.findByEmail(specialists.get(0).getEmail());
      //  System.out.println(CheckValidation.memberTypespecialist.getEmail());
        Map<SpecialistResult,Long> map=new HashMap<>();
        for (SpecialistResult specialistResult:specialists
        ) {
            System.out.println(specialistResult.getEmail());
            map.put(specialistResult,ordersService.numberOfOrders(specialistResult.getEmail(),"specialist")) ;
        }
        System.out.println(map);
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }
    @PostMapping("/searchCustomer")
    public ResponseEntity<Map<CustomerResult,Long>> searchCustomer(@RequestBody CustomerSearchDto customerSearchDto) {
        if (customerSearchDto.getRegisterTime()!=null){
            customerSearchDto.setRegisterTime(
                    LocalTime.of(customerSearchDto.getRegisterTime().getHour(),
                            customerSearchDto.getRegisterTime().getMinute(),
                            customerSearchDto.getRegisterTime().getSecond()+1));
        }
        if (customerSearchDto.getRegisterTime() == null) customerSearchDto.setRegisterTime(LocalTime.of(23,59));

        List<CustomerResult> customers = customerService.searchCustomer(customerSearchDto);
        Map<CustomerResult,Long> map=new HashMap<>();
        for (CustomerResult customerResult:customers
             ) {
            System.out.println(customerResult.getEmail());
           map.put(customerResult,ordersService.numberOfOrders(customerResult.getEmail(),"customer")) ;
        }
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    @PostMapping("/advanceSearch")
    public ResponseEntity<List<OrdersResult>> searchOrders(@RequestBody OrdersAdvanceSearchParameter ordersAdvanceSearchParameter) {
        List<OrdersResult> ordersResults = ordersService.searchInDuty(ordersAdvanceSearchParameter);
        if (ordersResults.size() == 0) {
            throw new CustomException("with this parameter not found any things");
        } else {
            return new ResponseEntity<>(ordersService.searchInDuty(ordersAdvanceSearchParameter), HttpStatus.ACCEPTED);
        }
    }


//    @PostMapping("/numberOfOrders")
//    public ResponseEntity<Long> numberOfOrders(@RequestBody CustomerDtoEmail customerDtoEmail) {
//        dtoValidation.isValid(customerDtoEmail);
//        Long numberOfOrders = ordersService.numberOfOrders(customerDtoEmail.getEmail());
//        try {
//            if (numberOfOrders == 0) {
//                throw new CustomException("with this email not found any orders");
//            } else {
//                return new ResponseEntity<>(numberOfOrders, HttpStatus.ACCEPTED);
//            }
//        } catch (CustomException e) {
//            throw new CustomException("with this email not found any member");
//        }
//    }
}
