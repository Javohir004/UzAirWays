package uz.jvh.uzairways.domain.DTO.response;

import lombok.*;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.domain.enumerators.UserRole;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private UUID uuid;
    private String username;
    private String surname;
    private String password;
    private UserRole role;
    private String email;
    private LocalDate birthDate;
    private String phoneNumber;
    private boolean enabled;
    private String address;
    private LocalDate createDate;
    private List<TickedResponse> ticketHistory;
    private String passportSeries;
}
