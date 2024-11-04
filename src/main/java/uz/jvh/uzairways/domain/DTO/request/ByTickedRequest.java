package uz.jvh.uzairways.domain.DTO.request;

import lombok.*;
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

    private LocalDateTime departureTime;

    private Integer passengers;

    private PaymentType paymentType;


}
