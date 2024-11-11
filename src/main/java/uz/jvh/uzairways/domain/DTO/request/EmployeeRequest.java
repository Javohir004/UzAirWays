package uz.jvh.uzairways.domain.DTO.request;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeRequest {
    private String username;
    private String firstName;
    private LocalDate birthDate;
    private String citizenship;
    private String serialNumber;
    private LocalDate validityPeriod;
}
