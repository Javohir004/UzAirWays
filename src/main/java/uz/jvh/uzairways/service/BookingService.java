package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.jvh.uzairways.domain.DTO.request.BookingRequest;
import uz.jvh.uzairways.domain.entity.Booking;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.respository.*;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    public Booking createBooking(BookingRequest request) {
        if (request.getUser() == null || request.getSeatNumber() == null || request.getFlight() == null) {
            throw new IllegalArgumentException("User, Seat yoki Flight ID null bo'lishi mumkin emas");
        }

        User user = userRepository.findById(request.getUser())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Flight flight = flightRepository.findById(request.getFlight()).
                orElseThrow(() -> new UsernameNotFoundException("Flight not found"));

        Ticket ticket = ticketRepository.findByFlightAndSeatNumber(flight, request.getSeatNumber())
                .orElseThrow(() -> new RuntimeException("Ticket not found for the selected flight and seat number"));


        if (!ticket.isAvailable()) {
            throw new RuntimeException("This place is already booked");
        }
        Optional<Flight> optionalFlight = flightRepository.findByDepartureAirportAndArrivalAirportAndDepartureTime(
                request.getDepartureAirport(),
                request.getArrivalAirport(),
                flight.getDepartureTime());

        if (optionalFlight.isEmpty()) {
            throw new RuntimeException("No flights were found for the selected destinations");
        }
        if (user.getBalance() < flight.getPrice()) {
            throw new RuntimeException("The balance is insufficient");
        }
        Booking booking = Booking.builder()
                .user(user)
                .flight(flight)
                .ticket(ticket)
                .price(flight.getPrice())
                .bookingDate(request.getBookingDate())
                .status(request.getStatus())
                .classType(request.getClassType())
                .build();

        user.setBalance(user.getBalance() - flight.getPrice());
        userRepository.save(user);

        ticket.setAvailable(false);
        ticketRepository.save(ticket);

        return bookingRepository.save(booking);
    }
}
