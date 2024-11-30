package uz.jvh.uzairways.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.DTO.request.FlightDTO;

import uz.jvh.uzairways.domain.DTO.response.AirPlaneResponse;
import uz.jvh.uzairways.domain.DTO.response.FlightResponse;
import uz.jvh.uzairways.domain.DTO.response.TicketDetailsResponse;
import uz.jvh.uzairways.domain.entity.AirPlane;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.domain.enumerators.Airport;
import uz.jvh.uzairways.domain.enumerators.ClassType;
import uz.jvh.uzairways.domain.exception.CustomException;
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
    private final AirPlaneService airPlaneService;
    private final TicketService ticketService;
    private final TicketRepository ticketRepository;

    @Transactional
    public FlightResponse saveFlight(FlightDTO flight) {
        if (flightRepository.existsByFlightNumber(flight.getFlightNumber())) {     //  shu raqamli reys  mavjud emasligi
            throw new CustomException("Flight number already exists !",4012, HttpStatus.CONFLICT);
        }
        if (flight.getDepartureAirport().equals(flight.getArrivalAirport())) {         // Jo'nash va kelish aeroportlari bir xil emasligiga
            throw new CustomException("Departure and arrival airports cannot be the same !",4013,HttpStatus.BAD_REQUEST);
        }
        if (flight.getDepartureTime().isAfter(flight.getArrivalTime()) ||
                flight.getArrivalTime().isBefore(flight.getDepartureTime())) {       //Jo'nash va kelish vaqtlari to'g'ri ekanligiga
            throw new CustomException("Departure time must be before arrival time !",4014,HttpStatus.BAD_REQUEST);
        }
        if (flightRepository.existsByDepartureAirportAndArrivalAirportAndDepartureTimeAndArrivalTimeAndIsActiveTrue(
                flight.getDepartureAirport(),
                flight.getArrivalAirport(),
                flight.getDepartureTime(),
                flight.getArrivalTime())) {                    // Ushbu sanada va vaqtda, shu aeroportlar bilan reys mavjud emasligiga
            throw new CustomException("A flight with the same departure and arrival airports at the same time already exists.",4015,HttpStatus.CONFLICT);
        }

        AirPlane airPlane = airPlaneRepository.findById(flight.getAirplane())
                .orElseThrow(() -> new CustomException("Airplane not found",4002, HttpStatus.NOT_FOUND));

        if (flightRepository.existsByAirplaneAndDepartureTimeAndArrivalTimeAndIsActiveTrue(
                airPlane,
                flight.getDepartureTime(),
                flight.getArrivalTime())) {               //Samolyot shu sanada va vaqtda boshqa reysda band emasliginiga
            throw new CustomException("The airplane is already scheduled for another flight at this time.",4017,HttpStatus.CONFLICT);
        }

        Flight flight1 = mapRequestToFlight(flight);
        flightRepository.save(flight1);
        ticketService.createTickets1(flight1);

        return mapToFlightResponse(flight1);
    }

    public FlightResponse mapToFlightResponse(Flight flight) {
        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setFlightNumber(flight.getFlightNumber());
        flightResponse.setFlightId(flight.getId());

        flightResponse.setDepartureAirport(flight.getDepartureAirport());
        flightResponse.setDepartureTime(flight.getDepartureTime());

        flightResponse.setArrivalAirport(flight.getArrivalAirport());
        flightResponse.setArrivalTime(flight.getArrivalTime());

        flightResponse.setAirplane(flight.getAirplane());
        flightResponse.setFlightStatus(flight.getFlightStatus());

        return flightResponse;
    }

    public Flight mapRequestToFlight(FlightDTO flight) {
        AirPlane airPlane = airPlaneRepository.findById(flight.getAirplane())
                .orElseThrow(() -> new CustomException("Airplane not found",4002, HttpStatus.NOT_FOUND));

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
        Flight byFlightId = flightRepository.findFlightByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException("Flight not found",4002, HttpStatus.NOT_FOUND));
        byFlightId.setActive(false);
        flightRepository.save(byFlightId);
    }

    @Transactional
    public void updateFlight(UUID flightId, FlightDTO flightDTO) {
        Flight flight = flightRepository.findFlightByIdAndIsActiveTrue(flightId)
                .orElseThrow(() -> new CustomException("Flight not found",4002, HttpStatus.NOT_FOUND));

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

    public List<FlightResponse> getAllFlights() {
        List<Flight> allByOrderByCreatedDesc = flightRepository.findAllByOrderByCreatedDescAndIsActiveTrue();
        return allByOrderByCreatedDesc.stream().map(flight ->
                        mapToFlightResponse(flight))
                .collect(Collectors.toList());
    }

    public FlightResponse getFlightById(UUID id) {
        Flight flight = flightRepository.findById(id).
                orElseThrow(() -> new CustomException("Flight not found",4002, HttpStatus.NOT_FOUND));
        return mapToFlightResponse(flight);
    }

    public List<AirPlaneResponse> getAvailableAircrafts(LocalDateTime departureTime , Airport flyingAirport) {
        List<AirPlane> availableAirplanes = flightRepository.findAvailableAirplanesAndIsActiveTrue(departureTime, flyingAirport);
        return availableAirplanes.stream()
                .map(airPlane -> airPlaneService.mapToResponse(airPlane))
                .collect(Collectors.toList());
    }

    public List<TicketDetailsResponse> getAllTicketDetailsByClassType(UUID flightId, ClassType classType) {
        List<Ticket> tickets = ticketRepository.findAllByFlightIdAndClassTypeAndIsBronFalseAndIsActiveTrue(flightId, classType);
        return tickets.stream()
                .map(ticket -> new TicketDetailsResponse(
                        ticket.getFlight().getId(),
                        ticket.getPrice(),
                        ticket.getClassType(),
                        ticket.getSeatNumber()
                )).collect(Collectors.toList());

    }

}
