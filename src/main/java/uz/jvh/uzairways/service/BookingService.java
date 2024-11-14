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


//    public List<TickedResponse> getBookingsByOwnerId(UUID ownerId) {
//        List<Booking> byUserIdAndIsActiveTrue = bookingRepository.findByUserIdAndIsActiveTrue(ownerId);
//        Booking booking = byUserIdAndIsActiveTrue.get(1);
//        LocalDateTime bookingDate = booking.getBookingDate();
//        BookingStatus status = booking.getStatus();
//        List<Employee> employees = booking.getEmployees();
//        User user = booking.getUser();
//        List<Ticket> tickets = booking.getTickets();
//        Flight flight = tickets.get(1).getFlight();
//        Double totalPrice = booking.getTotalPrice();
//        UUID id = booking.getId();
//  return null;
//    }


    public List<TickedResponse> getBookingsByOwnerId(UUID ownerId) {
        List<Booking> bookings = bookingRepository.findByUserIdAndIsActiveTrue(ownerId);

        return bookings.stream()
                .flatMap(booking -> booking.getTickets().stream().map(ticket -> toTickedResponse(ticket, booking)))
                .collect(Collectors.toList());
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
