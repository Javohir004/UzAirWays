package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.jvh.uzairways.domain.DTO.response.AirportInfo;
import uz.jvh.uzairways.domain.enumerators.Airport;

import java.util.List;

@RestController
@RequestMapping("/api/airport")
@RequiredArgsConstructor
public class AirPortController {

    @GetMapping("/airport-name/{name}")
    public String getAirportImage(@PathVariable String name) {
        String imageUrl = Airport.getImageUrlByAirport(name);
        if (imageUrl != null) {
            return imageUrl;
        } else {
            return "Airport not found";
        }
    }

    @GetMapping("/get-all-airports")
    public ResponseEntity<List<AirportInfo> >getAllAirports() {
        List<AirportInfo> allAirportsWithImages = Airport.getAllAirportsWithImages();
        return ResponseEntity.ok(allAirportsWithImages);
    }


}
