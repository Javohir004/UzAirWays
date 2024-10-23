package uz.jvh.uzairways.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.jvh.uzairways.domain.enumerators.ClassType;


@Entity
@Table(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Seat extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    private String seatNumber;  // e.g., "12A"

    private boolean isAvailable = true; // means false = booked , or true =  available

    @Enumerated(EnumType.STRING)
    private ClassType classType;

}
