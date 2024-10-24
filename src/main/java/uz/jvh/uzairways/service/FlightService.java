package uz.jvh.uzairways.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.respository.FlightRepository;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

}
