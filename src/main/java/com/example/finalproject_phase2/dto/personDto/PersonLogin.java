package com.example.finalproject_phase2.dto.personDto;

import lombok.*;
import lombok.experimental.FieldDefaults;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class PersonLogin {
    String email;
    String password;
}
