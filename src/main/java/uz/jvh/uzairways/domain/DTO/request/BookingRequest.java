package uz.jvh.uzairways.domain.DTO.request;

import lombok.*;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.domain.entity.Seat;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.domain.enumerators.Airport;
import uz.jvh.uzairways.domain.enumerators.BookingStatus;
import uz.jvh.uzairways.domain.enumerators.ClassType;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingRequest {

    private UUID user;

    private UUID flight;

    private LocalDateTime bookingDate;

    private BookingStatus status;

    private ClassType classType;

    private UUID seat;

    private Airport departureAirport;

    private Airport arrivalAirport;

}
