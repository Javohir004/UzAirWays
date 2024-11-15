package uz.jvh.uzairways.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.jvh.uzairways.domain.enumerators.UserRole;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    private String surname;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(unique = true, nullable = false)
    private String email;

    private LocalDate birthDate;

    @Column(nullable = false)
    private String phoneNumber;

    private Double balance;

    private String address;

    @Column(unique = true)
    private String verificationToken;

    @Column(nullable = false)
    private String passportSeries;


}
