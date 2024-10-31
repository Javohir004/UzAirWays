package uz.jvh.uzairways.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.DTO.request.FlightDTO;

import uz.jvh.uzairways.domain.entity.AirPlane;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.respository.FlightRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightService {

    private FlightRepository flightRepository;

    @Transactional
    public Flight saveFlight(FlightDTO flight) {
        Flight flight1 = mapRequestToFlight(flight);
      return flightRepository.save(flight1);
    }


    public Flight mapRequestToFlight(FlightDTO flight) {
        return Flight.builder().
                flightNumber(flight.getFlightNumber()).
                airplane(flight.getAirplane()).
                departureTime(flight.getDepartureTime()).
                arrivalTime(flight.getArrivalTime()).
                departureAirport(flight.getDepartureAirport()).
                arrivalAirport(flight.getArrivalAirport()).
                status(flight.getStatus()).
                build();
    }

    public void deleteFlight(UUID id) {
        Flight byFlightId = flightRepository.findByFlightId(id);
        byFlightId.setActive(false);
        flightRepository.save(byFlightId);
    }

    @Transactional
    public Flight updateFlight(UUID id, FlightDTO flight) {
        Flight byFlightId = flightRepository.findByFlightId(id);
        byFlightId.setFlightNumber(flight.getFlightNumber());
        byFlightId.setAirplane(flight.getAirplane());
        byFlightId.setDepartureTime(flight.getDepartureTime());
        byFlightId.setArrivalTime(flight.getArrivalTime());
        byFlightId.setDepartureAirport(flight.getDepartureAirport());
        byFlightId.setArrivalAirport(flight.getArrivalAirport());
        byFlightId.setStatus(flight.getStatus());
        return flightRepository.save(byFlightId);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(UUID id) {
        return flightRepository.findByFlightId(id);
    }

    public List<AirPlane> getAvailableAircrafts(LocalDateTime departureTime, LocalDateTime arrivalTime) {
        return flightRepository.findAvailableAirplanes(departureTime, arrivalTime);
    }
}
