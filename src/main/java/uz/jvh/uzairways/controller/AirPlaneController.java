package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jvh.uzairways.domain.DTO.request.AirPlaneDTO;
import uz.jvh.uzairways.domain.DTO.response.AirPlaneResponse;
import uz.jvh.uzairways.domain.entity.AirPlane;
import uz.jvh.uzairways.service.AirPlaneService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/airplane")
@RequiredArgsConstructor
public class AirPlaneController {

    private final AirPlaneService airPlaneService;


    // Create a new airplane
    @PostMapping("/create-plan")
    public ResponseEntity<String> create(@RequestBody AirPlaneDTO airPlaneDTO) {
        String result = airPlaneService.create(airPlaneDTO);
        return ResponseEntity.ok(result);
    }

    // Get all airplanes
    @GetMapping("/get-all")
    public ResponseEntity<List<AirPlaneResponse>> getAll() {
        List<AirPlaneResponse> allAirplanes = airPlaneService.findAll();
        return ResponseEntity.ok(allAirplanes);
    }

    // Get an airplane by ID
    @GetMapping("/findBy-Id{id}")
    public ResponseEntity<AirPlane> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(airPlaneService.findById(id));
    }

    // Update an airplane by ID
    @PutMapping("/update-airplane{id}")
    public ResponseEntity<Void> update(@RequestBody AirPlaneDTO airPlaneDTO, @PathVariable UUID id) {
        airPlaneService.update(airPlaneDTO, id);
        return ResponseEntity.noContent().build();
    }

    // Delete an airplane by ID
    @DeleteMapping("/delete{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        airPlaneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
