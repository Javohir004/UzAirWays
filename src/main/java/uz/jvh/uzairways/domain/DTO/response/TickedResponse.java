package uz.jvh.uzairways.domain.DTO.response;

import lombok.*;
import uz.jvh.uzairways.domain.enumerators.ClassType;
import uz.jvh.uzairways.domain.enumerators.FlightStatues;
import uz.jvh.uzairways.domain.enumerators.TicketStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TickedResponse {
    private String seatNumber;
    private double price;
    private LocalDateTime bookingDate;
    private ClassType classType;
    private Boolean nearWindow;
    private TicketStatus ticketStatus;

    // Parvoz ma'lumotlari
    private String flightNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String departureAirport;
    private String arrivalAirport;
    private FlightStatues flightStatus;

}
