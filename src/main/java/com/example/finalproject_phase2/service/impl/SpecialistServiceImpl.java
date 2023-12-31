package com.example.finalproject_phase2.service.impl;

import com.example.finalproject_phase2.custom_exception.CustomException;
import com.example.finalproject_phase2.custom_exception.CustomInputOutputException;
import com.example.finalproject_phase2.custom_exception.CustomNoResultException;
import com.example.finalproject_phase2.dto.specialistDto.*;
import com.example.finalproject_phase2.entity.*;
import com.example.finalproject_phase2.mapper.DutyMapper;
import com.example.finalproject_phase2.mapper.SubDutyMapper;
import com.example.finalproject_phase2.repository.SpecialistRepository;
import com.example.finalproject_phase2.securityConfig.AuthenticationResponse;
import com.example.finalproject_phase2.securityConfig.CustomUserDetailsService;
import com.example.finalproject_phase2.securityConfig.JwtService;
import com.example.finalproject_phase2.service.*;
import com.example.finalproject_phase2.mapper.SpecialistMapper;
import com.example.finalproject_phase2.service.email.EmailRequest;
import com.example.finalproject_phase2.service.email.MailService;
import com.example.finalproject_phase2.util.validation.CheckValidation;
import com.example.finalproject_phase2.entity.enumeration.SpecialistRegisterStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class SpecialistServiceImpl implements SpecialistService {
    private final SpecialistRepository specialistRepository;
    private final SpecialistMapper specialistMapper;
    private final WalletService walletService;
    CheckValidation checkValidation = new CheckValidation();
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final MailService mailService;
    private final DutyMapper dutyMapper;
    private final DutyService dutyService;
    private final SubDutyService subDutyService;
    private final SubDutyMapper subDutyMapper;

private String token;
    @Autowired
    public SpecialistServiceImpl(SpecialistRepository specialistRepository, SpecialistMapper specialistMapper, WalletService walletService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, CustomUserDetailsService customUserDetailsService, MailService mailService, DutyMapper dutyMapper, DutyService dutyService, SubDutyService subDutyService, SubDutyMapper subDutyMapper) {
        this.specialistRepository = specialistRepository;
        this.specialistMapper = specialistMapper;
        this.walletService = walletService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
        this.mailService = mailService;
        this.dutyMapper = dutyMapper;
        this.dutyService = dutyService;
        this.subDutyService = subDutyService;
        this.subDutyMapper = subDutyMapper;
    }
    @Override
    public AuthenticationResponse register(MultipartFile file,SpecialistRegisterDto specialistRegisterDto){
        EmailRequest emailRequest =new EmailRequest();
        Duty duty = dutyService.findByNames(specialistRegisterDto.getDutyName());
        SubDuty subDuty = subDutyService.findByNames(specialistRegisterDto.getSubDutyName());
        Set<SubDuty> subDuties=new HashSet<>();
        subDuties.add(subDuty);
        Specialist specialist=Specialist.builder().
        firstName(specialistRegisterDto.getFirstName())
       .lastName(specialistRegisterDto.getLastName())
       .email(specialistRegisterDto.getEmail())
               .password(specialistRegisterDto.getPassword())
                       .nationalId(specialistRegisterDto.getNationalId())
                               .subDuties(subDuties)
                                       .duty(duty).
                build();
        try{
            specialistRepository.findByEmail(specialistRegisterDto.getEmail()).ifPresentOrElse(
                tempCustomer -> {
                    throw new CustomException("Specialist with this email and password is exist ");
                }, () -> {
                        try {
                           specialist.setImageData(convertImageToImageDataFile(file,specialist));
                        } catch (CustomInputOutputException e) {
                            throw new CustomException("image have error");
                        }
                        specialist.setPassword(passwordEncoder.encode(specialist.getPassword()));
        Wallet wallet = walletService.createWallet();
        specialist.setWallet(wallet);
        specialist.setStatus(SpecialistRegisterStatus.WAITING_FOR_CONFIRM);
        specialist.setScore(0);
        specialist.setRegisterDate(LocalDate.now());
        specialist.setRegisterTime(LocalTime.now());
       specialist.setIsEnable(false);
        specialistRepository.save(specialist);
                });
            String jwtToken=jwtService.generateToken(customUserDetailsService.loadUserByUsername(specialist.getEmail()));
            this.token=jwtToken;
            CheckValidation.memberTypespecialist=specialist;
            emailRequest.setTo(specialist.getEmail());
            emailRequest.setSubject("activate account");
            emailRequest.setText("Click the following link to activate your account: http://localhost:8080/api/specialist/activate?token="+jwtToken);
            mailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());

            return  AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (CustomException c) {
            return  AuthenticationResponse.builder()
                    .token(c.getMessage())
                    .build();
        }
    }
    public boolean isAccountActivated(String token){
        System.out.println("this token"+ this.token);
        if (token.equals(this.token)){
            Specialist specialist = findByEmail(CheckValidation.memberTypespecialist.getEmail());
            specialist.setIsEnable(true);
            CheckValidation.memberTypespecialist.setIsEnable(true);
            specialistRepository.save(specialist);
            return true;
        }
        return false;
    }
    @Override
    public AuthenticationResponse authenticate(SpecialistLoginDto specialistLoginDto){
       try {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           specialistMapper.specialistLoginDtoToSpecialistLogin(specialistLoginDto)
                                   .getEmail(), specialistMapper.specialistLoginDtoToSpecialistLogin(specialistLoginDto).getPassword()
                   )
           );
           Specialist specialist = specialistRepository.findByEmail(specialistMapper.specialistLoginDtoToSpecialistLogin(specialistLoginDto).getEmail()).orElseThrow();
           CheckValidation.memberTypespecialist = specialist;
           String jwtToken = jwtService.generateToken(customUserDetailsService.loadUserByUsername(specialist.getEmail()));
           return  AuthenticationResponse.builder()
                   .token(jwtToken)
                   .build();
       }catch (CustomException ce){throw new CustomException("authenticate of this member have error "+ ce.getMessage());}

    }


    @Override
    public SpecialistEmailDto confirmSpecialistByAdmin(SpecialistEmailDto specialistEmailDto) {
        try {
            Specialist specialist1 = findByEmail(specialistEmailDto.getEmail());
            specialist1.setStatus(SpecialistRegisterStatus.CONFIRM);
            specialistRepository.save(specialist1);
        } catch (CustomNoResultException cnr) {
           throw new CustomNoResultException("confirm process have error");
        }
        return specialistEmailDto;
    }
    @Override
    public Boolean addSpecialistToSubDuty(SpecialistSubDutyDto specialistSubDutyDto) {
        specialistSubDutyDto.getSpecialist().getSubDuties().forEach(element -> {
            if (!specialistSubDutyDto.getSpecialist().getSubDuties().contains(specialistSubDutyDto.getSubDuty())) {
                specialistSubDutyDto.getSpecialist().getSubDuties().add(specialistSubDutyDto.getSubDuty());
                specialistRepository.save(specialistSubDutyDto.getSpecialist());
            } else {
                throw new CustomException("this specialist added to this subDuty before");
            }
        });
        return false;
    }
    @Override
    public void removeSpecialistFromDuty() {
        Set<Specialist> confirmSpecialist = new HashSet<>(specialistRepository.showConfirmSpecialist(SpecialistRegisterStatus.CONFIRM));
        if (confirmSpecialist.size() == 0) {
            System.out.println("no specialist unConfirm found");
        } else {
            for (Specialist specialist : confirmSpecialist
            ) {
                specialist.setStatus(SpecialistRegisterStatus.WAITING_FOR_CONFIRM);
                specialistRepository.save(specialist);
            }
        }
    }
    @Override
    public String convertImageToImageDataFile(MultipartFile file ,Specialist specialist) throws CustomInputOutputException {
        try {
            byte[] fileContent = IOUtils.toByteArray(file.getInputStream());
            if (!checkValidation.isJpgImage(fileContent))
                throw new CustomInputOutputException("image file format is not valid");
            if (!checkValidation.isImageHaveValidSize(fileContent)) {
                throw new CustomInputOutputException("image size must be lower than 300KB");
            }
            specialist.setImageData(Base64.getEncoder().encodeToString(fileContent));
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException | CustomInputOutputException e) {
            throw new CustomInputOutputException(e.getMessage());
        }
    }
@Override
    public String convertImageToImageData(SpecialistImageDto specialistImageDto) throws CustomInputOutputException {
        try {
            Specialist specialist = findByEmail(specialistImageDto.getEmail());
            if (specialist==null)throw new CustomInputOutputException("specialist not found");

            if (!checkValidation.isJpgImage(specialistImageDto.getImagePath()))
                throw new CustomInputOutputException("image file format is not valid with prefix without jpg");

            byte[] fileContent = FileUtils.readFileToByteArray(new File(specialistImageDto.getImagePath()));

            if (!checkValidation.isJpgImage(fileContent))
                throw new CustomInputOutputException("image file format is not valid");
            if (!checkValidation.isImageHaveValidSize(fileContent)) {
                throw new CustomInputOutputException("image size must be lower than 300KB");
            }
            specialist.setImageData(Base64.getEncoder().encodeToString(fileContent));
            specialistRepository.save(specialist);
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException | CustomInputOutputException e) {
            throw new CustomInputOutputException(e.getMessage());
        }
    }
@Override
    public void convertByteArrayToImage(ConvertImageDto convertImageDto) {
    try {
     Specialist specialist = findByEmail(convertImageDto.getEmail());
     if (specialist==null) { throw new CustomInputOutputException("no specialist found");}
            byte[] imageData = Base64.getDecoder().decode(specialist.getImageData());
            try (FileOutputStream fileOutputStream = new FileOutputStream(convertImageDto.getImagePath())) {
                fileOutputStream.write(imageData);
            }
    } catch (IOException |CustomInputOutputException e) {
        throw new CustomException(e.getMessage());
    }
    }

    @Override
    public Specialist findByEmail(String email) {
        return specialistRepository.findByEmail(email).get();
    }

    @Override
    public Optional<Specialist> findByEmailOptional(String email) {
        Optional<Specialist> specialist = specialistRepository.findByEmail(email);
        return specialist;
    }

    @Override
    public Integer updateSpecialistScore(SpecialistScoreDto specialistScoreDto) {
        specialistScoreDto.getSpecialist().setScore(specialistScoreDto.getScore());
        if (specialistScoreDto.getSpecialist().getScore()<0){
        specialistScoreDto.getSpecialist().setStatus(SpecialistRegisterStatus.WAITING_FOR_CONFIRM);
        }
        specialistRepository.save(specialistScoreDto.getSpecialist());
        return specialistScoreDto.getScore();
    }


    public Specification<Specialist> hasSpecialistWithThisEmail(String email) {
        return (specialist, cq, cb) -> cb.like(specialist.get("email"),"%"+ email+"%");
    }

    public  Specification<Specialist> hasSpecialistWithThisFirstName(String firstName) {
        return (specialist, cq, cb) -> cb.like(specialist.get("firstName"), "%" + firstName + "%");
    }

    public  Specification<Specialist> hasSpecialistWithThisLastName(String lastName) {
        return (specialist, cq, cb) -> cb.like(specialist.get("lastName"), "%" + lastName + "%");
    }
    public  Specification<Specialist> hasSpecialistWithThisNationalId(String nationalId) {
        return (specialist, cq, cb) -> cb.like(specialist.get("nationalId"), "%" + nationalId + "%");
    }
    public Specification<Specialist> hasSpecialistSubmitBeforeThisTime(LocalTime registerTime) {
        return (specialist, cq, cb) ->
                cb.lessThanOrEqualTo(specialist.get("registerTime"), registerTime);
    }

    @Override
    public List<SpecialistResult> searchSpecialist(SpecialistSearchDto specialistSearchDto) {
        Specialist searchSpecialist=specialistMapper.specialistSearchDtoToSpecialist(specialistSearchDto);
        List<SpecialistResult> specialistList=new ArrayList<>();
        specialistRepository.findAll(where(
                hasSpecialistWithThisEmail(searchSpecialist.getEmail())).
                and(hasSpecialistWithThisFirstName(searchSpecialist.getFirstName())).
                and(hasSpecialistWithThisLastName(searchSpecialist.getLastName())).
                and(hasSpecialistWithThisNationalId(searchSpecialist.getNationalId())).
                and(hasSpecialistSubmitBeforeThisTime(searchSpecialist.getRegisterTime()))
        ).forEach(specialist -> specialistList.add(new SpecialistResult(specialist.getFirstName(),specialist.getLastName(),specialist.getEmail())));
        System.out.println(specialistList.size());
            return specialistList;

    }
    @Override
    public boolean changePassword(String email, String newPassword){
        Specialist user = specialistRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("user not found"));
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        specialistRepository.save(user);
        return true;
    }
    @Override
     public boolean isConfirm(){
   if(CheckValidation.memberTypespecialist.getStatus() == SpecialistRegisterStatus.CONFIRM)return true;
   else throw new CustomException("can not permission to this method please wait that admin confirm");
    }
}