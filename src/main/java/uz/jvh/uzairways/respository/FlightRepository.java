package uz.jvh.uzairways.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.domain.enumerators.Airport;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {

    Optional<Flight> findByDepartureAirportAndArrivalAirportAndDepartureTime(Airport departureAirport,
                                                                             Airport arrivalAirport,
                                                                             LocalDateTime departureTime);

}
