package uz.jvh.uzairways.domain.DTO.response;
import lombok.*;
import uz.jvh.uzairways.domain.entity.AirPlane;
import uz.jvh.uzairways.domain.enumerators.Airport;
import uz.jvh.uzairways.domain.enumerators.FlightStatues;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class FlightResponse {

    private UUID flightId;

    private AirPlane airplane;

    private String flightNumber;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private Airport departureAirport;

    private Airport arrivalAirport;

    private FlightStatues flightStatus;

}
