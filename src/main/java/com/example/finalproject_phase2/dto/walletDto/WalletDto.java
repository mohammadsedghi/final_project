package com.example.finalproject_phase2.dto.walletDto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletDto {
    @NotNull(message = "Balance of wallet must be have value")
    @Pattern(message = "Balance of wallet must be have positive value",regexp = "^[+]?\\d+([.]\\d+)?$")
    Double Balance;
}
