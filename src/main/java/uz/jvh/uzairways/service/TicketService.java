package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.DTO.request.ByTickedRequest;
import uz.jvh.uzairways.domain.DTO.request.TicketDTO;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.domain.enumerators.AircraftType;
import uz.jvh.uzairways.domain.enumerators.ClassType;
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

    public Ticket mapRequestToTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        Flight flight = flightRepository.findById(ticketDTO.getFlight()).orElseThrow(() -> new RuntimeException("Flight not found"));
        ticket.setSeatNumber(ticketDTO.getSeatNumber());
        ticket.setPrice(ticketDTO.getPrice());
        ticket.setClassType(ticketDTO.getClassType());
        ticket.setFlight(flight);
        ticket.setBookingDate(ticketDTO.getBookingDate());
        return ticket;
    }


    public List<Ticket> getFlightInfo(ByTickedRequest request) {

        if (request.getPassengers() > 5) {
            throw new IllegalArgumentException("Passengers must be less than 5");
        }
        Flight flight = flightRepository.findFirstByDepartureAirportAndArrivalAirportAndDepartureTime(
                request.getDepartureAirport(),
                request.getArrivalAirport(),
                request.getDepartureTime());

        if (flight == null) {
            throw new IllegalArgumentException("Flight not found");
        }
        List<Ticket> availableTickets = ticketRepository.findAllByIsBronAndFlight(
                false,
                flight
        );

        if (request.getPassengers() > availableTickets.size()) {
            throw new IllegalArgumentException("Passengers exceeds number of tickets");
        }
        return availableTickets;
    }


    public void createTickets1(Flight flight) {
        AircraftType aircraftType = flight.getAirplane().getAircraftType();
        List<Ticket> tickets = new ArrayList<>();

        Map<ClassType, Double> classPrices = new HashMap<>();
        classPrices.put(ClassType.BUSINESS, 1000d);
        classPrices.put(ClassType.FIRST, 500d);
        classPrices.put(ClassType.ECONOMY, 200d);

        Map<AircraftType, int[]> aircraftSeats = new HashMap<>();
        aircraftSeats.put(AircraftType.JET, new int[]{20, 10, 30}); // Business, First Class, Economy
        aircraftSeats.put(AircraftType.PROPELLER, new int[]{40, 20, 60});

        if (!aircraftSeats.containsKey(aircraftType)) {
            return;
        }

        int[] availableSeats = aircraftSeats.get(aircraftType);


        for (ClassType classType : ClassType.values()) {
            createTicketsByClass(tickets, flight, availableSeats, classPrices.get(classType), classType);
        }
        ticketRepository.saveAll(tickets);
    }


    private void createTicketsByClass(List<Ticket> tickets, Flight flight, int[] availableSeats, Double price, ClassType classType) {
        int classIndex = classType.ordinal(); // ClassType dan indeks olish
        for (int j = 0; j < availableSeats[classIndex]; j++) {
            Ticket ticket = Ticket.builder()
                    .flight(flight)
                    .isBron(false)
                    .price(price)
                    .classType(classType)
                    .build();
            tickets.add(ticket);
        }
    }

}
