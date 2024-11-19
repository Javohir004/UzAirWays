package uz.jvh.uzairways.domain.DTO.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class PasswordResetDTO {
    private String token; // Tasdiqlash tokeni
    private String newPassword; // Yangi parol
    private String confirmPassword; // Par
}
