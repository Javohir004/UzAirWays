package uz.jvh.uzairways.domain.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "employee")
public class Employee extends BaseEntity {
    private String username;
    private String firstName;
    private LocalDate birthDate;
    private String citizenship;
    private String serialNumber;
    private LocalDate validityPeriod;
}
