package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jvh.uzairways.domain.DTO.request.FlightDTO;
import uz.jvh.uzairways.domain.DTO.response.TickedResponse;
import uz.jvh.uzairways.domain.DTO.response.TicketDetailsResponse;
import uz.jvh.uzairways.domain.entity.AirPlane;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.domain.enumerators.Airport;
import uz.jvh.uzairways.domain.enumerators.ClassType;
import uz.jvh.uzairways.service.FlightService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/flight")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;


    @PostMapping("/create-flight")
    public ResponseEntity<Flight> createFlight(@RequestBody FlightDTO flightDto) {
        return ResponseEntity.ok(flightService.saveFlight(flightDto));
    }
  
    @GetMapping("/all-flight")
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
    public ResponseEntity<List<AirPlane>> getAvailableAirplanes(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") LocalDateTime departureTime ,
                                                                @RequestParam Airport flightAirport) {

        List<AirPlane> availableAirplanes = flightService.getAvailableAircrafts(departureTime , flightAirport);

        return ResponseEntity.ok(availableAirplanes);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable UUID id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok("Parvoz o'chirildi");
    }

    @GetMapping("/tickets-class-type/{id}")
    public ResponseEntity<List<TicketDetailsResponse>> getAllTicketDetailsByClassType(@PathVariable UUID id,
                                                                                      @RequestParam ClassType classType) {
        List<TicketDetailsResponse> allTicketDetailsByClassType = flightService.getAllTicketDetailsByClassType(id, classType);
            return ResponseEntity.ok(allTicketDetailsByClassType);
    }


    @PutMapping("/update-flight/{flightId}")
    public ResponseEntity<Void> updateFlight(@PathVariable UUID flightId, @RequestBody FlightDTO flightDto) {
        flightService.updateFlight(flightId, flightDto);
        return ResponseEntity.ok().build();
    }
}
