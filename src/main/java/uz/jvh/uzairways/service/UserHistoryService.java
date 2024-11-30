package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.entity.Booking;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.domain.exception.CustomException;
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
            throw new CustomException("userId cannot be null",4011, HttpStatus.NOT_FOUND);
        }
        return bookingRepository.findByUserIdAndIsActiveTrue(userId);
    }

}
