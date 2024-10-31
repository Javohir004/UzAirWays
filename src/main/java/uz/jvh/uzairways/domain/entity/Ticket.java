package uz.jvh.uzairways.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import uz.jvh.uzairways.domain.enumerators.ClassType;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;
    /// qasyi parvozga tegishli

    private double price;

    private String seatNumber;  /// o'rindiq raqami
    /// o'rindiq raqami

    @Enumerated(EnumType.STRING)
    private ClassType classType;

    private Boolean nearWindow;  /// deraza yonidami

    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;      /// chipta statusi


    @CreationTimestamp
    private LocalDateTime bookingDate; // chipta olingan kun

    private boolean isAvailable = true;

    private boolean isAvailable = true;
}
