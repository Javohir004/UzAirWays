package uz.jvh.uzairways.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.jvh.uzairways.domain.entity.AirPlane;
import uz.jvh.uzairways.domain.entity.Flight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {

    Flight findByFlightId(UUID flightId);

    Flight findByFlightName(String flightName);

    Flight findByFlightCode(String flightCode);

    Flight findByFlightDate(String flightDate);

    Flight findByFlightTime(String flightTime);

    Flight findByFlightDateAndFlightTime(String flightDate, String flightTime);

    @Query("SELECT f.airplane FROM Flight f WHERE f.departureTime > :arrivalTime OR f.arrivalTime < :departureTime")
    List<AirPlane> findAvailableAirplanes(LocalDateTime departureTime, LocalDateTime arrivalTime);


}
