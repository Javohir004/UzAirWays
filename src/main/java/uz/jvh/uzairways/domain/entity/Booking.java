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
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;
    /// parvoz

    @ManyToOne
    private Seat seat;

    @Column(nullable = false)
    private Double price;

    @CreationTimestamp
    private LocalDateTime bookingDate;
    /// bron qilingan kun

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    ///

    @Enumerated(EnumType.STRING)
    private ClassType classType;


    private boolean isPaid = false;


}
