package uz.jvh.uzairways.domain.DTO.response;

import lombok.*;
import uz.jvh.uzairways.domain.enumerators.ClassType;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TicketDetailsResponse {
    private UUID flightId;
    private double ticketPrice;
    private ClassType classType;
    private String seatNumber;
}
