package uz.jvh.uzairways.domain.DTO.response;
import lombok.*;
import uz.jvh.uzairways.domain.enumerators.UserRole;
import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserResponse {
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
}
