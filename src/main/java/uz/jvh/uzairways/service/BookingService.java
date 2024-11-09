package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.DTO.request.ByTickedRequest;
import uz.jvh.uzairways.domain.entity.Booking;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.domain.enumerators.BookingStatus;
import uz.jvh.uzairways.respository.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;



    public void bronTicked(List<Ticket> tickets, ByTickedRequest request) {
        for (int i = 0; i < request.getPassengers(); i++) {
            Ticket ticket = tickets.get(i);
            ticket.setActive(true);
            ticketRepository.save(ticket);

            Booking booking = Booking.builder()
                    .ticket(ticket)
                    .totalPrice(ticket.getPrice())
                    .status(BookingStatus.CONFIRMED)
                    .build();

            bookingRepository.save(booking);
        }
    }
}
