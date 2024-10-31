package uz.jvh.uzairways.domain.DTO.request;
import lombok.*;
import uz.jvh.uzairways.domain.entity.AirPlane;
import uz.jvh.uzairways.domain.enumerators.Airport;
import uz.jvh.uzairways.domain.enumerators.FlightStatues;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FlightDTO {

    private AirPlane airplane;

    private String flightNumber;

    private LocalDateTime departureTime; // jo'nash vaqti

    private LocalDateTime arrivalTime;

    private Airport departureAirport; // uchish airaporti

    private Airport arrivalAirport;

    private FlightStatues status;
}
