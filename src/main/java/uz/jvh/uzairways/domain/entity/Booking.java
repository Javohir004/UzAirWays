package uz.jvh.uzairways.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import uz.jvh.uzairways.domain.enumerators.BookingStatus;
import uz.jvh.uzairways.domain.enumerators.ClassType;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Booking extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne
    private Ticket ticket;

    @Column(nullable = false)
    private Double totalPrice;

    @CreationTimestamp
    private LocalDateTime bookingDate;


    @Enumerated(EnumType.STRING)
    private BookingStatus status;


}
