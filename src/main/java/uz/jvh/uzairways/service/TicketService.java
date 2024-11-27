package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.jvh.uzairways.domain.DTO.request.ByTickedRequest;
import uz.jvh.uzairways.domain.DTO.request.TicketDTO;
import uz.jvh.uzairways.domain.DTO.response.TicketResponse;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.domain.enumerators.AircraftType;
import uz.jvh.uzairways.domain.enumerators.ClassType;
import uz.jvh.uzairways.domain.exception.CustomException;
import uz.jvh.uzairways.respository.FlightRepository;
import uz.jvh.uzairways.respository.TicketRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;


    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(UUID id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chipta topilmadi: " + id));
    }

    public Ticket updateTicket(UUID id, Ticket ticket) {
        Ticket existingTicket = getTicketById(id);
        existingTicket.setId(id);
        existingTicket.setFlight(ticket.getFlight());
        existingTicket.setSeatNumber(ticket.getSeatNumber());
        existingTicket.setPrice(ticket.getPrice());
        existingTicket.setClassType(ticket.getClassType());
        return ticketRepository.save(existingTicket);
    }

    public void deleteTicket(UUID id) {
        Ticket existingTicket = getTicketById(id);
        existingTicket.setActive(false);
        ticketRepository.save(existingTicket);
    }

    public List<TicketResponse> getFlightInfo(ByTickedRequest request) {

        if (request.getPassengers() > 5) {
            throw new CustomException("Passengers must be less than 5", 4002, HttpStatus.BAD_REQUEST);
        }

        Flight flight = flightRepository.findFirstByDepartureAirportAndArrivalAirportAndDepartureTime(
                        request.getDepartureAirport(),
                        request.getArrivalAirport(),
                        request.getDepartureTime())
                .orElseThrow(() -> new CustomException("Flight not found", 4041, HttpStatus.NOT_FOUND));

        List<Ticket> availableTickets = ticketRepository.findAllByIsBronAndFlight(
                false,
                flight
        );

        if (availableTickets.isEmpty()) {
            throw new CustomException("No available tickets for the selected flight", 4042, HttpStatus.NOT_FOUND);
        }

        if (request.getPassengers() > availableTickets.size()) {
            throw new CustomException("Passengers exceeds number of tickets", 4002, HttpStatus.BAD_REQUEST);
        }

        return mapToTicketResponse(availableTickets, flight);
    }

    @Transactional
    public void createTickets1(Flight flight) {
        AircraftType aircraftType = flight.getAirplane().getAircraftType();
        List<Ticket> tickets = new ArrayList<>();

        Map<ClassType, Double> classPrices = new HashMap<>();
        classPrices.put(ClassType.BUSINESS, 800d);
        classPrices.put(ClassType.FIRST, 500d);
        classPrices.put(ClassType.ECONOMY, 200d);

        Map<AircraftType, int[]> aircraftSeats = new HashMap<>();
        aircraftSeats.put(AircraftType.JET, new int[]{20, 10, 30}); // Business, First Class, Economy
        aircraftSeats.put(AircraftType.PROPELLER, new int[]{40, 20, 60});

        if (!aircraftSeats.containsKey(aircraftType)) {
            return;
        }

        int[] availableSeats = aircraftSeats.get(aircraftType);

        // Har bir ClassType uchun chiptalarni yaratish
        for (ClassType classType : ClassType.values()) {
            createTicketsByClass(tickets, flight, availableSeats, classPrices.get(classType), classType);
        }
        ticketRepository.saveAll(tickets);
    }

    private void createTicketsByClass(List<Ticket> tickets, Flight flight, int[] availableSeats, Double price, ClassType classType) {
        int classIndex = classType.ordinal(); // ClassType dan indeks olish
        int seatCount = availableSeats[classIndex]; // O'rinlar sonini olish

        // Har bir o'rin uchun chipta yaratish
        for (int j = 0; j < seatCount; j++) {
            // seatNumberni avtomatik ravishda yaratish: masalan, "B1", "F1", "E1"
            String seatNumber = classType.name().substring(0, 1) + (j + 1);  // B -> Business, F -> First, E -> Economy

            Ticket ticket = Ticket.builder()
                    .flight(flight)
                    .isBron(false)  // Bron qilish holati
                    .price(price)
                    .classType(classType)
                    .seatNumber(seatNumber)  // seatNumberni belgilash
                    .build();
            tickets.add(ticket);
        }
    }

    private List<TicketResponse> mapToTicketResponse(List<Ticket> tickets, Flight flight) {
        return tickets.stream()
                .map(ticket -> TicketResponse.builder()
                        .ticketId(ticket.getId())
                        .flightNumber(flight.getFlightNumber())
                        .departureTime(flight.getDepartureTime())
                        .arrivalTime(flight.getArrivalTime()) // Flight'dan olish
                        .isBron(ticket.isBron())
                        .price(ticket.getPrice())
                        .classType(ticket.getClassType())
                        .build())
                .toList();
    }
}
