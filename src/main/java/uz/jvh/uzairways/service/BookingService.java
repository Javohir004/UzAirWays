package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.DTO.request.EmployeeRequest;
import uz.jvh.uzairways.domain.entity.Booking;
import uz.jvh.uzairways.domain.entity.Employee;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.domain.enumerators.BookingStatus;
import uz.jvh.uzairways.respository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
                throw new RuntimeException("Ticket is Bron");
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
}
