package uz.jvh.uzairways.domain.DTO.request;
import lombok.*;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.domain.enumerators.BookingStatus;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingDTO {

    private User user;

    private Flight flight;

    private LocalDateTime bookingDate;

    private BookingStatus status;

}
