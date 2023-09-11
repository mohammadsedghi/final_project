package com.example.finalproject_phase2.dto.customerCommentsDto;

import com.example.finalproject_phase2.entity.Orders;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CustomerCommentsDto {
    Long  ordersId;
    @Pattern(message = "description of CustomerComments must be just letters",regexp = "^[a-zA-Z]+$")
    @NotNull(message = "description of  CustomerComments must be have value")
    String description;
    @NotNull(message = "score of CustomerComments must be have value")
    @Range(min = 0,max = 5,message = "score must be between 0 to 5")
    Integer score;
}
