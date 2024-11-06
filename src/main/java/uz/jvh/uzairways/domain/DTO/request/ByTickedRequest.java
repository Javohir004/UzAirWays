package uz.jvh.uzairways.domain.DTO.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import uz.jvh.uzairways.domain.enumerators.Airport;
import uz.jvh.uzairways.domain.enumerators.PaymentType;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ByTickedRequest {
    private Airport departureAirport;
    private Airport arrivalAirport;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;
    private Integer passengers;
}
