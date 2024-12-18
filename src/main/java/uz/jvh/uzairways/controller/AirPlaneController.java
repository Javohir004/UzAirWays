package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jvh.uzairways.domain.DTO.request.AirPlaneDTO;
import uz.jvh.uzairways.domain.DTO.response.AirPlaneResponse;
import uz.jvh.uzairways.service.AirPlaneService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/airplane")
@RequiredArgsConstructor
public class AirPlaneController {

    private final AirPlaneService airPlaneService;



    @PostMapping("create-airplane")
    public ResponseEntity<String> create(@RequestBody AirPlaneDTO airPlaneDTO) {
        String result = airPlaneService.create(airPlaneDTO);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/get-all")
    public ResponseEntity<List<AirPlaneResponse>> getAll() {
        List<AirPlaneResponse> allAirplanes = airPlaneService.findAll();
        return ResponseEntity.ok(allAirplanes);
    }


    @GetMapping("/findBy-Id{id}")
    public ResponseEntity<AirPlaneResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(airPlaneService.findById(id));
    }


    @PutMapping("/update-airplane{id}")
    public ResponseEntity<Void> update(@RequestBody AirPlaneDTO airPlaneDTO, @PathVariable UUID id) {
        airPlaneService.update(airPlaneDTO, id);
        return ResponseEntity.noContent().build();
    }



    @DeleteMapping("/delete{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        airPlaneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
