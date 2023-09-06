package com.example.finalproject_phase2.dto.addressDto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDto {
    @NotNull(message = "province must be have value")
    @Pattern(message = "province must be just letters",regexp = "^[a-zA-Z]+$")
    String province;
    @NotNull(message = "city must be have value")
    @Pattern(message = "city must be just letters",regexp = "^[a-zA-Z]+$")
    String city;
    @NotNull(message = "street must be have value")
    @Pattern(message = "street must be just letters",regexp = "^[a-zA-Z]+$")
    String street;
    @NotNull(message = "postalCode must be have value")
    @Pattern(message = "postalCode must be just digit",regexp ="^\\d+$")
    String postalCode;
    @NotNull(message = "houseNumber must be have value")
    @Digits(integer = 10,fraction = 0,message = "houseNumber must be number")
    Integer houseNumber;



}
