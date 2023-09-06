package com.example.finalproject_phase2.dto.customerDto;

import com.example.finalproject_phase2.dto.personDto.PersonDto;
import com.example.finalproject_phase2.dto.personDto.PersonSearchDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
//@AllArgsConstructor()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerSearchDto extends PersonSearchDto {
    public CustomerSearchDto(@NotNull(message = "firstName must be have value") @Length(message = "firstName must be 100 character", max = 100) @Pattern(message = "firstName must be just letters", regexp = "^[a-zA-Z]+$") String firstName, @NotNull(message = "lastName must be have value") @Length(message = "lastName must be 100 character", max = 100) @Pattern(message = "lastname must be just letters", regexp = "^[a-zA-Z]+$") String lastName, @NotNull(message = "nationalId must be have value") @Length(message = "national id must be 10 digit", min = 10, max = 10) @Pattern(message = "nationalId must be just digit", regexp = "^\\d+$") String nationalId, @NotNull(message = "email must be have value") @Pattern(message = "email is not valid", regexp = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$") String email, @NotNull(message = "password must be have value") @Pattern(message = "password is not valid", regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$") @Length(message = "password must be 8 character", min = 8, max = 8) String password, @NotNull(message = "registerTime must be have value") LocalTime registerTime) {
        super(firstName, lastName, nationalId, email, password, registerTime);
    }
    // LocalTime registerTime;


}
