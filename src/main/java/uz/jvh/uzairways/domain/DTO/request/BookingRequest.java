package uz.jvh.uzairways.domain.DTO.request;

import lombok.*;
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

    private LocalDateTime bookingDate;

    private BookingStatus status;

    private Double TotalPrice;

}
