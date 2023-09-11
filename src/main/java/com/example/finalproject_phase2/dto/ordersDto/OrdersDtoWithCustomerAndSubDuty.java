package com.example.finalproject_phase2.dto.ordersDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersDtoWithCustomerAndSubDuty {
    @NotNull(message = "email must be have value")
    @Pattern(message = "email is not valid",regexp = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$")
    String customerEmail;
    @NotNull(message = "subDutyName must be have value")
    @Length(message ="subDutyName must be less than 100 character",max = 100)
    @Pattern(message = "subDutyName must be just letters",regexp = "^[a-zA-Z]+$")
    String subDutyName;
}
