package uz.jvh.uzairways.domain.DTO.request;
import lombok.*;
import uz.jvh.uzairways.domain.entity.Booking;
import uz.jvh.uzairways.domain.entity.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentDTO {

    private User user;


    private Booking booking;

    private double amount;

    private LocalDateTime paymentDate;

    private String paymentMethod;  // e.g., "Credit Card", "PayPal"
}
