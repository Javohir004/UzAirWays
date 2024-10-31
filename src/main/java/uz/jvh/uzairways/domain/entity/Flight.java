package uz.jvh.uzairways.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.jvh.uzairways.domain.enumerators.FlightStatues;


import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Flight extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "airplane_id", nullable = false)
    private AirPlane airplane;

    @Column(unique = true, nullable = false)
    private String flightNumber;  /// 2 yoki 3 chi kabi  reys

    private LocalDateTime departureTime; // jo'nash vaqti

    private LocalDateTime arrivalTime; /// qo'nish vaqti

    private String departureAirport; // uchish airaporti

    private String arrivalAirport;  // qo'nish vaqti

    @Enumerated(EnumType.STRING)
    private FlightStatues status; /// parvoz statusi


}
