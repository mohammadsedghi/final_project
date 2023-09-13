package com.example.finalproject_phase2.dto.ordersDto;

import com.example.finalproject_phase2.entity.Address;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubmitOrderDto {
    @NotNull(message = "customerEmail must be have value")
    @Pattern(message = "customerEmail is not valid",regexp = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$")
    String customerEmail;
    @NotNull(message = "specialistEmail must be have value")
    @Pattern(message = "specialistEmail is not valid",regexp = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$")
    String specialistEmail;
    @NotNull(message = "subDutyId must be have value")
    Long subDutyId;
    @NotNull(message = "address must be have value")
    Address address;
    @NotNull(message = "proposedPrice must be have value")
    String proposedPrice;
    @NotNull(message = "description must be have value")
    @Length(message ="description must be 100 character",max = 100)
    String description;
    @NotNull(message = "dateOfWork must be have value")
    LocalDate dateOfWork;
    @NotNull(message = "timeOfWork must be have value")
    LocalTime timeOfWork;


}
