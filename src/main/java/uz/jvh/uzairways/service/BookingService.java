package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.jvh.uzairways.domain.DTO.request.BookingRequest;
import uz.jvh.uzairways.domain.entity.Booking;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.domain.entity.Seat;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.respository.*;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;

    @Transactional
    public Booking createBooking(BookingRequest request) {
        if (request.getUser() == null || request.getSeat() == null || request.getFlight() == null) {
            throw new IllegalArgumentException("User, Seat yoki Flight ID null bo'lishi mumkin emas");
        }

        User user = userRepository.findById(request.getUser()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Seat seat = seatRepository.findById(request.getSeat()).orElseThrow(() -> new UsernameNotFoundException("Seat not found"));
        Flight flight = flightRepository.findById(request.getFlight()).orElseThrow(() -> new UsernameNotFoundException("Flight not found"));

        if (!seat.isAvailable()) {
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
                .seat(seat)
                .price(flight.getPrice())
                .bookingDate(request.getBookingDate())
                .status(request.getStatus())
                .classType(request.getClassType())
                .build();

        user.setBalance(user.getBalance() - flight.getPrice());
        userRepository.save(user);

        seat.setAvailable(false);
        seatRepository.save(seat);

        return bookingRepository.save(booking);
    }
}
