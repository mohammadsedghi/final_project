package com.example.finalproject_phase2.service;

import com.example.finalproject_phase2.custom_exception.CustomInputOutputException;
import com.example.finalproject_phase2.dto.ProjectResponse;
import com.example.finalproject_phase2.dto.adminDto.AdminLoginDto;
import com.example.finalproject_phase2.dto.specialistDto.*;
import com.example.finalproject_phase2.entity.Admin;
import com.example.finalproject_phase2.entity.Specialist;
import com.example.finalproject_phase2.entity.SubDuty;
import com.example.finalproject_phase2.securityConfig.AuthenticationResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface SpecialistService {
//     AuthenticationResponse register(MultipartFile file,SpecialistDto specialistDto);
     AuthenticationResponse register(MultipartFile file,SpecialistRegisterDto specialistRegisterDto);
    boolean isAccountActivated(String token);
    AuthenticationResponse authenticate(SpecialistLoginDto specialistLoginDto);
    SpecialistDto confirmSpecialistByAdmin(SpecialistDto specialistDto);
    Boolean addSpecialistToSubDuty(SpecialistSubDutyDto specialistSubDutyDto);
    boolean changePassword(String email,String password );
    void removeSpecialistFromDuty();
     String convertImageToImageData(SpecialistImageDto specialistImageDto) throws CustomInputOutputException;
    String convertImageToImageDataFile(MultipartFile file , Specialist specialist)throws CustomInputOutputException;
     void convertByteArrayToImage (ConvertImageDto convertImageDto );
     Specialist findByEmail(String email);
    Integer updateSpecialistScore(SpecialistScoreDto specialistScoreDto);
     List<SpecialistResult> searchSpecialist(SpecialistDto specialistDto);
}
