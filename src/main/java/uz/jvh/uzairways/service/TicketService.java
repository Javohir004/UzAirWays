package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.DTO.request.TicketDTO;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.domain.enumerators.ClassType;
import uz.jvh.uzairways.domain.enumerators.TicketStatus;
import uz.jvh.uzairways.respository.FlightRepository;
import uz.jvh.uzairways.respository.TicketRepository;
import uz.jvh.uzairways.respository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;

    /// miyyanga ... o'zincha o'chirib tashlama yoqmasa tegma kommentga olib qo'y


    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(UUID id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chipta topilmadi: " + id));
    }

    public Ticket createTicket(TicketDTO ticket) {
        Ticket ticket1 = mapRequestToTicket(ticket);
        return ticketRepository.save(ticket1);
    }


    public List<Ticket> createTickets(TicketDTO ticketDTO, String aircraftType) {
        List<Ticket> tickets = new ArrayList<>();

        // O'rindiq turlariga mos narxlar
        Float businessClassPrice = 1000F;
        Float firstClassPrice = 500F;
        Float economyClassPrice = 200F;

        // Samolyot turi asosida o'rindiq va narxlarni belgilash
        int[] availableSeats;
        if ("jet".equalsIgnoreCase(aircraftType)) {
            availableSeats = new int[]{20, 10, 30}; // Business, First Class, Economy
        } else if ("propeller".equalsIgnoreCase(aircraftType)) {
            availableSeats = new int[]{40, 20, 60}; // Business, First Class, Economy
        } else {
            return tickets; // Agar samolyot turi noto'g'ri bo'lsa, bo'sh ro'yxat qaytaramiz
        }

        // O'rindiqlarni yaratish
        createTicketsByClass(tickets, ticketDTO, availableSeats, businessClassPrice, ClassType.BUSINESS);
        createTicketsByClass(tickets, ticketDTO, availableSeats, firstClassPrice, ClassType.FIRST);
        createTicketsByClass(tickets, ticketDTO, availableSeats, economyClassPrice, ClassType.ECONOMY);

        // Chiptalarni saqlash
        return ticketRepository.saveAll(tickets);
    }

    private void createTicketsByClass(List<Ticket> tickets, TicketDTO ticketDTO, int[] availableSeats, Float price, ClassType classType) {
        int classIndex = classType.ordinal(); // ClassType dan indeks olish
        for (int j = 0; j < availableSeats[classIndex]; j++) {
            Ticket ticket = mapRequestToTicket(ticketDTO);
            ticket.setPrice(price);
            ticket.setClassType(classType);
            tickets.add(ticket);
        }
    }


    public Ticket updateTicket(UUID id, Ticket ticket) {
        Ticket existingTicket = getTicketById(id);
        existingTicket.setId(id);
        existingTicket.setFlight(ticket.getFlight());
        existingTicket.setTicketStatus(ticket.getTicketStatus());
        existingTicket.setSeatNumber(ticket.getSeatNumber());
        existingTicket.setNearWindow(ticket.getNearWindow());
        existingTicket.setOwner(ticket.getOwner());
        existingTicket.setPrice(ticket.getPrice());
        existingTicket.setClassType(ticket.getClassType());
        return ticketRepository.save(existingTicket);
    }

    public void deleteTicket(UUID id) {
        Ticket existingTicket = getTicketById(id);
        existingTicket.setActive(false);
        ticketRepository.save(existingTicket);
    }

    public Ticket mapRequestToTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        User owner = userRepository.findById(ticketDTO.getOwner()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Flight flight = flightRepository.findById(ticketDTO.getFlight()).orElseThrow(() -> new RuntimeException("Flight not found"));
        ticket.setTicketStatus(ticketDTO.getTicketStatus());
        ticket.setSeatNumber(ticketDTO.getSeatNumber());
        ticket.setTicketStatus(ticketDTO.getTicketStatus());
        ticket.setOwner(owner);
        ticket.setPrice(ticketDTO.getPrice());
        ticket.setClassType(ticketDTO.getClassType());
        ticket.setFlight(flight);
        ticket.setBookingDate(ticketDTO.getBookingDate());
        return ticket;
    }


    public void cancelTicked(UUID ticketId) {
        if (ticketId == null) {
            throw new IllegalArgumentException("Ticket ID cannot be null");
        }
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
        LocalDateTime departureTime = ticket.getFlight().getDepartureTime();
        LocalDateTime now = LocalDateTime.now();

        if (departureTime.isAfter(now)) {
            ticket.setTicketStatus(TicketStatus.CANCELLED);
            ticketRepository.save(ticket);
        } else {
            throw new IllegalArgumentException("Ticket already cancelled");
        }
    }
}
