package uz.jvh.uzairways.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.jvh.uzairways.service.FlightService;

@Controller
@RequestMapping("/flight")
@AllArgsConstructor
public class FlightController {

    @Autowired
    private FlightService flightService;

}
