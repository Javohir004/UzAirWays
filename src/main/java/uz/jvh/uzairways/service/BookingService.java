package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.jvh.uzairways.domain.DTO.request.EmployeeRequest;
import uz.jvh.uzairways.domain.DTO.response.TickedResponse;
import uz.jvh.uzairways.domain.entity.*;
import uz.jvh.uzairways.domain.enumerators.BookingStatus;
import uz.jvh.uzairways.domain.enumerators.UserRole;
import uz.jvh.uzairways.domain.exception.CustomException;
import uz.jvh.uzairways.respository.*;

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

    @Transactional
    public Booking createBooking(UUID userId, List<UUID> ticketIds, List<EmployeeRequest> employees) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new CustomException("User note found", 4002, HttpStatus.NOT_FOUND);
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
                throw new CustomException("Ticket not found", 4002, HttpStatus.NOT_FOUND);
            }
            Ticket ticket = ticketOptional.get();

            if (ticket.isBron()) {
                throw new CustomException("Ticket is already Bron", 4012, HttpStatus.CONFLICT);
            }
            ticket.setBron(true);
            ticketRepository.save(ticket);

            bookedTickets.add(ticket);
            totalPrice += ticket.getPrice();
        }
        if (user.getBalance() < totalPrice) {
            throw new CustomException("Insufficient balance for the booking", 4012, HttpStatus.BAD_REQUEST);
        }
        user.setBalance(user.getBalance() - totalPrice);

        Optional<User> ownerOptional = userRepository.findUserByRoleAndIsActiveTrue(UserRole.OWNER);
        if (ownerOptional.isEmpty()) {
            throw new CustomException("User not found", 4002, HttpStatus.NOT_FOUND);
        }

        User owner = ownerOptional.get();
        owner.setBalance(owner.getBalance() + totalPrice);
        userRepository.save(owner);

        return createAndSaveBooking(user, bookingEmployees, bookedTickets, totalPrice);
    }

    /**
     * bu user ni barcha chiptalarini olib keladi
     **/
    public List<TickedResponse> getBookingsByOwnerId(UUID ownerId) {
        List<Booking> bookings = bookingRepository.findActiveBookingsByUserId(ownerId);

        return bookings.stream()
                .flatMap(booking -> booking.getTickets().stream().map(ticket -> toTickedResponse(ticket, booking)))
                .collect(Collectors.toList());
    }

    /**
     * muddati o'tgan chiptalar yani history uchun
     **/
    public List<TickedResponse> getExpiredTicketsByUserId(UUID userId) {
        List<Booking> bookings = bookingRepository.findActiveBookingsByUserId(userId);

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

    /**
     * bronni ichidagi hali foydalanilmagan chiptalar
     **/
    public List<TickedResponse> getActiveTicketsByUserId(UUID userId) {
        List<Booking> bookings = bookingRepository.findActiveBookingsByUserId(userId);

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

    private Booking createAndSaveBooking(User user, List<Employee> employees, List<Ticket> tickets, double totalPrice) {
        Booking booking = Booking.builder()
                .user(user)
                .employees(employees)
                .tickets(tickets)
                .totalPrice(totalPrice)
                .bookingDate(LocalDateTime.now())
                .status(BookingStatus.CONFIRMED)
                .build();
        return bookingRepository.save(booking);
    }


}
