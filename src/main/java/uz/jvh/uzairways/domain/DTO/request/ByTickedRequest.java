package uz.jvh.uzairways.domain.DTO.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureTime;

    private Integer passengers;

    private PaymentType paymentType;


}
