package com.example.finalproject_phase2.dto.customerDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResult{
    String name;
    String family;
    String email;



}
