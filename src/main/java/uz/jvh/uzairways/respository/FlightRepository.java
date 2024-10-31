package uz.jvh.uzairways.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.jvh.uzairways.domain.entity.AirPlane;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.domain.enumerators.Airport;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {

    Optional<Flight> findByDepartureAirportAndArrivalAirportAndDepartureTime(Airport departureAirport,
                                                                             Airport arrivalAirport,
                                                                             LocalDateTime departureTime);
    Flight findFlightById(UUID flightId);

    Flight findFlightByDepartureTime(LocalDateTime departureTime);

    Flight findByFlightNumber(String flightNumber);


    @Query("SELECT f.airplane FROM Flight f WHERE f.departureTime > :arrivalTime OR f.arrivalTime < :departureTime")
    List<AirPlane> findAvailableAirplanes(LocalDateTime departureTime, LocalDateTime arrivalTime);


}
