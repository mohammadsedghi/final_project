package com.example.finalproject_phase2.dto.specialistDto;

import com.example.finalproject_phase2.dto.personDto.PersonChangePasswordDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpecialistChangePasswordDto extends PersonChangePasswordDto {
    @Builder
    public SpecialistChangePasswordDto(@NotNull(message = "email must be have value") @Pattern(message = "email is not valid", regexp = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$") String email, @NotNull(message = "password must be have value") @Pattern(message = "password is not valid", regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$") @Length(message = "password must be 8 character", min = 8, max = 8) String oldPassword, @NotNull(message = "password must be have value") @Pattern(message = "password is not valid", regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$") @Length(message = "password must be 8 character", min = 8, max = 8) String newPassword) {
        super(email, oldPassword, newPassword);
    }
}
