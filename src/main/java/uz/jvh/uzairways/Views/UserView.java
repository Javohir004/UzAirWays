package uz.jvh.uzairways.Views;

import uz.jvh.uzairways.domain.enumerators.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public interface UserView {
    UUID getId();
    String getUsername();
    String getSurname();
    String getPassword();
    UserRole getRole();
    String getEmail();
    LocalDate getBirthDate();
    String getPhoneNumber();
    float getBalance();
    boolean isEnabled();
    String getAddress();
    LocalDateTime getCreated();

    // Default method to format created date
    default String getFormattedCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return getCreated() != null ? getCreated().format(formatter) : null; // Handle null case
    }
}
