package uz.jvh.uzairways.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.jvh.uzairways.domain.enumerators.ClassType;


@Entity
@Table(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Seat extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;             /// qaysi parvozga tegishli

    private String seatNumber;  // e.g., "12A"

    private boolean isAvailable = true; // means false = booked , or true =  available
                                        // bron qilindimi yoki yo'q

    @Enumerated(EnumType.STRING)
    private ClassType classType; /// bisnesmi yoki ekanom



}
