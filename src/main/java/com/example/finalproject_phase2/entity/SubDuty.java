package com.example.finalproject_phase2.entity;

import com.example.finalproject_phase2.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubDuty extends BaseEntity<Long> {
    @ManyToOne
    Duty duty;
     String name;
    Double basePrice;
    String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubDuty subDuty = (SubDuty) o;
        return Objects.equals(duty, subDuty.duty) && Objects.equals(name, subDuty.name) && Objects.equals(basePrice, subDuty.basePrice) && Objects.equals(description, subDuty.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duty, name, basePrice, description);
    }

    @Override
    public String toString() {
        return "SubDuty{" +
                "duty=" + duty.getName()+
                ", name='" + name + '\'' +
                ", basePrice=" + basePrice +
                ", description='" + description + '\'' +
                "} " + super.toString();
    }
}
