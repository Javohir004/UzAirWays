package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.entity.Booking;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.respository.BookingRepository;
import uz.jvh.uzairways.respository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserHistoryService {
    private final BookingRepository bookingRepository;

    public List<Booking> getUserBooking(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return bookingRepository.findByUserId(userId);
    }

}
