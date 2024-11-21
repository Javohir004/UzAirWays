package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.jvh.uzairways.domain.enumerators.Airport;

@RestController
@RequestMapping("/api/about-us")
@RequiredArgsConstructor
public class AirPortController {

    @GetMapping("/api/airport/{name}")
    public String getAirportImage(@PathVariable String name) {
        String imageUrl = Airport.getImageUrlByAirport(name);
        if (imageUrl != null) {
            return imageUrl;
        } else {
            return "Airport not found";
        }
    }

}
