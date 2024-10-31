package uz.jvh.uzairways.domain.DTO.request;
import lombok.*;
import uz.jvh.uzairways.domain.enumerators.ClassType;
import uz.jvh.uzairways.domain.enumerators.TicketStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TicketDTO {
    private UUID owner;
    private UUID flight;
    private double price;
    private String seatNumber; // e.g., "12A"
    private ClassType classType;
    private boolean nearWindow;
    private TicketStatus ticketStatus;
    private LocalDateTime bookingDate;

    /// seat entity va ticket bitta dto da
    private boolean isAvailable = true; // means false = booked , or true =  available


}
