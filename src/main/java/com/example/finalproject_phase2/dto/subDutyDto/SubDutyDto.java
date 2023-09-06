package com.example.finalproject_phase2.dto.subDutyDto;

import com.example.finalproject_phase2.entity.Duty;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SubDutyDto {
    Duty duty;
    @NotNull(message = "name must be have value")
    @Pattern(message = "name must be just letters",regexp = "^[a-zA-Z]+$")
    @Length(message ="name must be 100 character",max = 100)
    String name;
    @NotNull(message = "basePrice of SubDuty must be have value")
     @Positive(message = "base price must be positive")
    // @Pattern(message = "basePrice must be have positive value",regexp = "^[+]?\\d+([.]\\d+)?$")
    Double basePrice;
    @NotNull(message = "this field must be have value")
    @Pattern(message = "province must be just letters",regexp = "^[a-zA-Z]+$")
    @Length(message ="lastName must be 100 character",max = 100)
    String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubDutyDto that = (SubDutyDto) o;
        return Objects.equals(duty, that.duty) && Objects.equals(name, that.name) && Objects.equals(basePrice, that.basePrice) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duty, name, basePrice, description);
    }

    @Override
    public String toString() {
        return "SubDutyDto{" +
                "duty=" + duty.getName() +
                ", name='" + name + '\'' +
                ", basePrice=" + basePrice +
                ", description='" + description + '\'' +
                '}';
    }
}
