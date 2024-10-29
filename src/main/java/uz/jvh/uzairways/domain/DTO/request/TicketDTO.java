package uz.jvh.uzairways.domain.DTO.request;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.domain.enumerators.ClassType;
import uz.jvh.uzairways.domain.enumerators.TicketStatus;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TicketDTO {
    private User owner;
    private Flight flight;
    private double price;
    private String seatNumber; // e.g., "12A"
    private ClassType classType;
    private boolean nearWindow;
    private TicketStatus ticketStatus;
    private LocalDateTime bookingDate;

    /// seat entity va ticket bitta dto da
    private boolean isAvailable = true; // means false = booked , or true =  available


}
