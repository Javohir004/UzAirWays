package uz.jvh.uzairways.domain.DTO.response;

import lombok.*;

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
    private String classType;
    private Boolean nearWindow;
    private String ticketStatus;

    // Parvoz ma'lumotlari
    private String flightNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String departureAirport;
    private String arrivalAirport;
    private String flightStatus;
}
