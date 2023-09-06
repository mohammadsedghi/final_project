package com.example.finalproject_phase2.entity;

import com.example.finalproject_phase2.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerComments extends BaseEntity<Long> {
    @OneToOne
    Orders orders;
    @Pattern(message = "description of CustomerComments must be just letters",regexp = "^[a-zA-Z]+$")
    @NotNull(message = "description of  CustomerComments must be have value")
    String description;
    @NotNull(message = "score of CustomerComments must be have value")
    @Range(min = 0,max = 5,message = "score must be between 0 to 5")
    Integer score;
}
