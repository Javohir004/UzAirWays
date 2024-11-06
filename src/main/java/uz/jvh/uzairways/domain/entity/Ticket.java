package uz.jvh.uzairways.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import uz.jvh.uzairways.domain.enumerators.ClassType;
import uz.jvh.uzairways.domain.enumerators.PaymentType;
import uz.jvh.uzairways.domain.enumerators.TicketStatus;


import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Ticket extends BaseEntity {
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    private Double price;

    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private ClassType classType;

    private LocalDateTime bookingDate;

    private boolean isBron;
}
