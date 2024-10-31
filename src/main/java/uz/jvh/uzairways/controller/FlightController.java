package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jvh.uzairways.domain.DTO.request.FlightDTO;
import uz.jvh.uzairways.domain.entity.AirPlane;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.service.FlightService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;



    @PostMapping("/create")
    public ResponseEntity<Flight> createFlight(@RequestBody FlightDTO flightDto) {
        return ResponseEntity.ok(flightService.saveFlight(flightDto));
    }


    @GetMapping("/get-all")
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }


    // 4. Parvozni ID bo‘yicha olish
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable UUID id) {
        Flight flightById = flightService.getFlightById(id);
        return ResponseEntity.ok(flightById);
    }

    @GetMapping("/get-available-airplanes")
    public ResponseEntity<List<AirPlane>> getAvailableAirplanes(@RequestParam LocalDateTime departureTime,
                                                                @RequestParam LocalDateTime arrivalTime) {

        List<AirPlane> availableAirplanes = flightService.getAvailableAircrafts(departureTime, arrivalTime);

        return ResponseEntity.ok(availableAirplanes);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable UUID id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok("Parvoz o'chirildi");
    }
}
