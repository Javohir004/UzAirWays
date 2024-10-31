package uz.jvh.uzairways.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.domain.entity.Ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    List<Ticket> findByOwnerId(UUID userId);

    Optional<Ticket> findByFlightAndSeatNumber(Flight flight, String seatNumber);

    List<Ticket> findAllByOwnerId(UUID userId);

    List<Ticket> findByOwner_IdAndFlight_DepartureTimeAfter(UUID userId, LocalDateTime now);

}
