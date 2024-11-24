package uz.jvh.uzairways.domain.DTO.response;

import lombok.*;
import uz.jvh.uzairways.domain.enumerators.ClassType;
import uz.jvh.uzairways.domain.enumerators.FlightStatues;
import uz.jvh.uzairways.domain.enumerators.TicketStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TickedResponse {

    private UUID ticketId;
    private String seatNumber;
    private double price;
    private LocalDateTime bookingDate;
    private ClassType classType;
    private Boolean isBron;

    // Parvoz ma'lumotlari
    private UUID flightId;
    private String flightNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String departureAirport;
    private String arrivalAirport;
    private FlightStatues flightStatus;

}
