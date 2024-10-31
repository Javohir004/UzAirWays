package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.entity.Booking;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.respository.BookingRepository;
import uz.jvh.uzairways.respository.TicketRepository;
import uz.jvh.uzairways.respository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserHistoryService {
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final UserService userService;

    public List<Booking> getUserBooking(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return bookingRepository.findByUserId(userId);
    }

    public List<Ticket> getUserTicket(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return ticketRepository.findByOwnerId(userId);
    }

    public List<Ticket> getAllUserTickets(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return ticketRepository.findAllByOwnerId(userId);
    }

    public List<Ticket> getUpcomingUserTickets(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        LocalDateTime now = LocalDateTime.now();
        return ticketRepository.findByOwner_IdAndFlight_DepartureTimeAfter(userId, now);
    }
}
