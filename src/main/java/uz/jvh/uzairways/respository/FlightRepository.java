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
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {

    Optional<Flight> findFlightByIdAndIsActiveTrue(UUID flightId);


   /** bu yerda kirib kelgan departure vaqti flight dataBazasidagi flight larning arrival vaqtidan katta bo'lishi kerak
    * va arrival airaporti kirib kelgan departure airaportiga teng bo'lishi kerak**/
    @Query("SELECT f.airplane FROM Flight f WHERE f.arrivalTime < :departureTime AND f.arrivalAirport = :flyingAirport")
    List<AirPlane> findAvailableAirplanesAndIsActiveTrue(@Param("departureTime") LocalDateTime departureTime,
                                          @Param("flyingAirport") Airport flyingAirport);



    /**metodi uchish aeroporti, kelish aeroporti va uchish vaqti bo‘yicha eng yaqin flight qidiradi.**/
    @Query("SELECT f FROM Flight f WHERE f.departureAirport = :departureAirport " +
            "AND f.arrivalAirport = :arrivalAirport " +
            "AND f.departureTime >= :startTime " +
            "ORDER BY f.departureTime ASC")
    Optional<Flight> findFirstByDepartureAirportAndArrivalAirportAndDepartureTimeAndIsActiveTrue(
            @Param("departureAirport") Airport departureAirport,
            @Param("arrivalAirport") Airport arrivalAirport,
            @Param("startTime") LocalDateTime startTime);


 boolean existsByFlightNumber(String flightNumber);

    boolean existsByAirplaneAndDepartureTimeAndArrivalTimeAndIsActiveTrue(
            AirPlane airplane, LocalDateTime departureTime, LocalDateTime arrivalTime

    );

    /**Bu metod Flight jadvallari orasida departureAirport, arrivalAirport, departureTime, va arrivalTime
    qiymatlari berilgan qiymatlarga teng bo‘lgan yozuv bor-yo‘qligini tekshiradi.
      */
    boolean existsByDepartureAirportAndArrivalAirportAndDepartureTimeAndArrivalTimeAndIsActiveTrue(
            Airport departureAirport,
            Airport arrivalAirport,
            LocalDateTime departureTime,
            LocalDateTime arrivalTime
    );

    @Query("SELECT f FROM Flight f WHERE f.departureTime > :departureTime AND f.isActive = true ORDER BY f.created DESC")
    List<Flight> findAllByDepartureDateAfterAndIsActiveTrue(@Param("departureTime") LocalDateTime departureTime);



}
