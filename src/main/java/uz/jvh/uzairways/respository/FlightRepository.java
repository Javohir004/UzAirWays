package uz.jvh.uzairways.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.jvh.uzairways.domain.entity.AirPlane;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.domain.enumerators.Airport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {

//    Optional<Flight> findByDepartureAirportAndArrivalAirportAndDepartureTime(Airport departureAirport,
//                                                                             Airport arrivalAirport,
//                                                                             LocalDateTime departureTime);

    Flight findFlightById(UUID flightId);



    @Query("SELECT f.airplane FROM Flight f WHERE f.arrivalTime < :departureTime AND f.arrivalAirport = :flyingAirport")
    List<AirPlane> findAvailableAirplanes(@Param("departureTime") LocalDateTime departureTime,
                                          @Param("flyingAirport") Airport flyingAirport);


    @Query("SELECT f FROM Flight f WHERE f.departureAirport = :departureAirport " +
            "AND f.arrivalAirport = :arrivalAirport " +
            "AND f.departureTime >= :startTime " +
            "ORDER BY f.departureTime ASC")
    Flight findFirstByDepartureAirportAndArrivalAirportAndDepartureTime(
            @Param("departureAirport") Airport departureAirport,
            @Param("arrivalAirport") Airport arrivalAirport,
            @Param("startTime") LocalDateTime startTime);


    boolean existsByFlightNumber(String flightNumber);

    boolean existsByAirplaneAndDepartureTimeAndArrivalTime(
            AirPlane airplane, LocalDateTime departureTime, LocalDateTime arrivalTime
    );

    boolean existsByDepartureAirportAndArrivalAirportAndDepartureTimeAndArrivalTime(
            Airport departureAirport,
            Airport arrivalAirport,
            LocalDateTime departureTime,
            LocalDateTime arrivalTime
    );

}
