package uz.jvh.uzairways.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.domain.enumerators.ClassType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    Optional<Ticket> findByFlightAndSeatNumber(Flight flight, String seatNumber);

    List<Ticket> findByFlight_DepartureTimeAfter(LocalDateTime now);

    List<Ticket> findAllByFlightIdAndClassTypeAndIsBronFalseAndIsActiveTrue(UUID flightId, ClassType classType);

    List<Ticket> findAllByIsBronAndFlightAndIsActiveTrue(Boolean isBron, Flight flight);

    List<Ticket> findAllIsActiveTrue();

    Optional<Ticket> findByIdAndIsActiveTrue(UUID id);
}
