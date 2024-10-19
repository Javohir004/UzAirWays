package uz.jvh.uzairways.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.jvh.uzairways.domain.enumerators.FlightStatues;


import java.time.LocalDateTime;

//@Entity
//@Table(name = "flights")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Flight extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "airplane_id", nullable = false)
    private AirPlane airplane;

    @Column(unique = true, nullable = false)
    private String flightNumber;

    private LocalDateTime departureTime; // jo'nash vaqti

    private LocalDateTime arrivalTime;

    private String departureAirport; // uchish airaporti

    private String arrivalAirport;

    @Enumerated(EnumType.STRING)
    private FlightStatues status;


}
