package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.DTO.request.EmployeeRequest;
import uz.jvh.uzairways.domain.DTO.response.TickedResponse;
import uz.jvh.uzairways.domain.entity.*;
import uz.jvh.uzairways.domain.enumerators.BookingStatus;
import uz.jvh.uzairways.respository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;


    public Booking createBooking(UUID userId, List<UUID> ticketIds, List<EmployeeRequest> employees) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        User user = optionalUser.get();

        List<Employee> bookingEmployees = new ArrayList<>();

        for (EmployeeRequest employee : employees) {
            Optional<Employee> employeeOptional = employeeRepository.findByUsername(employee.getUsername());
            Employee bookingEmployee;

            if (employeeOptional.isPresent()) {
                bookingEmployee = employeeOptional.get();
            } else {
                Employee employeeRequest = new Employee(
                        employee.getUsername(),
                        employee.getFirstName(),
                        employee.getBirthDate(),
                        employee.getCitizenship(),
                        employee.getSerialNumber(),
                        employee.getValidityPeriod()
                );
                bookingEmployee = employeeRepository.save(employeeRequest);
            }
            bookingEmployees.add(bookingEmployee);
        }

        List<Ticket> bookedTickets = new ArrayList<>();

        double totalPrice = 0.0;
        for (UUID ticketId : ticketIds) {
            Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
            if (ticketOptional.isEmpty()) {
                throw new RuntimeException("Ticket not found");
            }
            Ticket ticket = ticketOptional.get();

            if (ticket.isBron()) {
                throw new RuntimeException("Ticket is already Bron");
            }
            ticket.setBron(true);
            ticketRepository.save(ticket);

            bookedTickets.add(ticket);
            totalPrice += ticket.getPrice();
        }   
        if (user.getBalance() < totalPrice) {
            throw new IllegalArgumentException("Insufficient balance for the booking");
        }
        user.setBalance(user.getBalance() - totalPrice);

        Booking booking = Booking.builder()
                .user(user)
                .employees(bookingEmployees)
                .tickets(bookedTickets)
                .totalPrice(totalPrice)
                .bookingDate(LocalDateTime.now())
                .status(BookingStatus.CONFIRMED)
                .build();
        return bookingRepository.save(booking);
    }

   /** bu user ni barcha chiptalarini olib keladi **/
    public List<TickedResponse> getBookingsByOwnerId(UUID ownerId) {
        List<Booking> bookings = bookingRepository.findByUserIdAndIsActiveTrue(ownerId);

        return bookings.stream()
                .flatMap(booking -> booking.getTickets().stream().map(ticket -> toTickedResponse(ticket, booking)))
                .collect(Collectors.toList());
    }

    /** muddati o'tgan chiptalar yani history uchun**/
    public List<TickedResponse> getExpiredTicketsByUserId(UUID userId) {
        List<Booking> bookings = bookingRepository.findByUserIdAndIsActiveTrue(userId);

        LocalDateTime currentDateTime = LocalDateTime.now();

        List<TickedResponse> expiredTickets = new ArrayList<>();

        for (Booking booking : bookings) {
            for (Ticket ticket : booking.getTickets()) {
                Flight flight = ticket.getFlight();

                // Muddati o'tgan ticketni tekshirish
                if (flight.getDepartureTime().isBefore(currentDateTime)) {
                    TickedResponse ticketResponse = toTickedResponse(ticket, booking);
                    expiredTickets.add(ticketResponse);
                }
            }
        }

        return expiredTickets;
    }

    /** bronni ichidagi hali foydalanilmagan chiptalar **/
    public List<TickedResponse> getActiveTicketsByUserId(UUID userId) {
        List<Booking> bookings = bookingRepository.findByUserIdAndIsActiveTrue(userId);

        LocalDateTime currentDateTime = LocalDateTime.now();

        List<TickedResponse> activeTickets = new ArrayList<>();

        for (Booking booking : bookings) {
            for (Ticket ticket : booking.getTickets()) {
                Flight flight = ticket.getFlight();

                // Muddati o'tmagan ticketni tekshirish
                if (flight.getDepartureTime().isAfter(currentDateTime)) {
                    TickedResponse ticketResponse = toTickedResponse(ticket, booking);
                    activeTickets.add(ticketResponse);
                }
            }
        }

        return activeTickets;
    }

    private TickedResponse toTickedResponse(Ticket ticket, Booking booking) {
        Flight flight = ticket.getFlight();

        return TickedResponse.builder()
                .ticketId(ticket.getId())
                .seatNumber(ticket.getSeatNumber())
                .price(ticket.getPrice())
                .bookingDate(booking.getBookingDate())
                .classType(ticket.getClassType())
                .isBron(ticket.isBron())
                // Parvoz ma'lumotlari
                .flightId(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .departureAirport(flight.getDepartureAirport().toString())
                .arrivalAirport(flight.getArrivalAirport().toString())
                .flightStatus(flight.getFlightStatus())
                .build();
    }

}
