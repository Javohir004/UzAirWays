package uz.jvh.uzairways.domain.DTO.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import uz.jvh.uzairways.domain.enumerators.ClassType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponse {
    private UUID ticketId;
    private String flightNumber;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivalTime;
    private Double price ;
    private ClassType classType;
    private boolean isBron;
    private String seatNumber;
}
