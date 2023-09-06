package com.example.finalproject_phase2.dto.specialistDto;

import com.example.finalproject_phase2.dto.personDto.PersonLogin;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpecialistLogin extends PersonLogin {
    @Builder
    public SpecialistLogin(String email, String password) {
        super(email, password);
    }
}
