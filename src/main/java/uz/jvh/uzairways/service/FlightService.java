package uz.jvh.uzairways.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.DTO.request.FlightDTO;

import uz.jvh.uzairways.domain.DTO.response.TicketDetailsResponse;
import uz.jvh.uzairways.domain.entity.AirPlane;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.domain.enumerators.Airport;
import uz.jvh.uzairways.domain.enumerators.ClassType;
import uz.jvh.uzairways.respository.AirPlaneRepository;
import uz.jvh.uzairways.respository.FlightRepository;
import uz.jvh.uzairways.respository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Lazy
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirPlaneRepository airPlaneRepository;
    private final TicketService ticketService;
    private final TicketRepository ticketRepository;

    @Transactional
    public Flight saveFlight(FlightDTO flight) {
        if (flightRepository.existsByFlightNumber(flight.getFlightNumber())) {     //  shu raqamli reys  mavjud emasligi
            throw new IllegalArgumentException("Flight number already exists !");
        }
        if (flight.getDepartureAirport().equals(flight.getArrivalAirport())) {         // Jo'nash va kelish aeroportlari bir xil emasligiga
            throw new IllegalArgumentException("Departure and arrival airports cannot be the same !");
        }
        if (flight.getDepartureTime().isAfter(flight.getArrivalTime()) ||
                flight.getArrivalTime().isBefore(flight.getDepartureTime())) {       //Jo'nash va kelish vaqtlari to'g'ri ekanligiga
            throw new IllegalArgumentException("Departure time must be before arrival time !");
        }
        if (flightRepository.existsByDepartureAirportAndArrivalAirportAndDepartureTimeAndArrivalTime(
                flight.getDepartureAirport(),
                flight.getArrivalAirport(),
                flight.getDepartureTime(),
                flight.getArrivalTime())) {                    // Ushbu sanada va vaqtda, shu aeroportlar bilan reys mavjud emasligiga
            throw new IllegalArgumentException("A flight with the same departure and arrival airports at the same time already exists.");
        }

        AirPlane airPlane = airPlaneRepository.findById(flight.getAirplane())
                .orElseThrow(() -> new IllegalArgumentException("Airplane not found"));

        if (flightRepository.existsByAirplaneAndDepartureTimeAndArrivalTime(
                airPlane,
                flight.getDepartureTime(),
                flight.getArrivalTime())) {               //Samolyot shu sanada va vaqtda boshqa reysda band emasliginiga
            throw new IllegalArgumentException("The airplane is already scheduled for another flight at this time.");
        }

        Flight flight1 = mapRequestToFlight(flight);
        flightRepository.save(flight1);
        ticketService.createTickets1(flight1);
        return flight1;
    }


    public Flight mapRequestToFlight(FlightDTO flight) {
        AirPlane airPlane = airPlaneRepository.findById(flight.getAirplane())
                .orElseThrow(() -> new RuntimeException("Airplane not found"));

        return Flight.builder()
                .flightNumber(flight.getFlightNumber())
                .airplane(airPlane)
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .departureAirport(flight.getDepartureAirport())
                .arrivalAirport(flight.getArrivalAirport())
                .flightStatus(flight.getStatus())
                .build();
    }

    public void deleteFlight(UUID id) {
        Flight byFlightId = flightRepository.findFlightById(id);
        byFlightId.setActive(false);
        flightRepository.save(byFlightId);
    }

    @Transactional
    public void updateFlight(UUID flightId, FlightDTO flightDTO) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found"));

        if (flightDTO.getFlightNumber() != null) {
            flight.setFlightNumber(flightDTO.getFlightNumber());
        }
        if (flightDTO.getDepartureTime() != null) {
            flight.setDepartureTime(flightDTO.getDepartureTime());
        }
        if (flightDTO.getArrivalTime() != null) {
            flight.setArrivalTime(flightDTO.getArrivalTime());
        }
        if (flightDTO.getDepartureAirport() != null) {
            flight.setDepartureAirport(flightDTO.getDepartureAirport());
        }
        if (flightDTO.getArrivalAirport() != null) {
            flight.setArrivalAirport(flightDTO.getArrivalAirport());
        }
        if (flightDTO.getStatus() != null) {
            flight.setFlightStatus(flightDTO.getStatus());
        }

        flightRepository.save(flight);
    }


    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(UUID id) {
        return flightRepository.findFlightById(id);
    }


    public List<AirPlane> getAvailableAircrafts(LocalDateTime departureTime , Airport flyingAirport) {
        return flightRepository.findAvailableAirplanes(departureTime , flyingAirport);
    }


    public List<TicketDetailsResponse> getAllTicketDetailsByClassType(UUID flightId, ClassType classType) {
        List<Ticket> tickets = ticketRepository.findAllByFlightIdAndClassTypeAndIsBronFalse(flightId, classType);
        return tickets.stream()
                .map(ticket -> new TicketDetailsResponse(
                        ticket.getFlight().getId(),
                        ticket.getPrice(),
                        ticket.getClassType(),
                        ticket.getSeatNumber()
                )).collect(Collectors.toList());

    }
}
