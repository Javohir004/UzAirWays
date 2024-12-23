package uz.jvh.uzairways.domain.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "employee")
public class Employee extends BaseEntity {
    private String username;
    private String firstName;
    private LocalDate birthDate;
    private String citizenship;
    private String serialNumber;
    private LocalDate validityPeriod;

}
