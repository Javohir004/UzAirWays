package uz.jvh.uzairways.service;


import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.DTO.request.FlightDTO;
import uz.jvh.uzairways.domain.entity.Flight;
import uz.jvh.uzairways.respository.FlightRepository;

import java.util.List;
import java.util.UUID;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public String createFlight(FlightDTO flight) {
        Flight map = modelMapper.map(flight, Flight.class);
        flightRepository.save(map);
        return "success";
    }

    @Transactional
    public void updateFlight(FlightDTO flight , UUID flightId) {
        Flight map = modelMapper.map(flight, Flight.class);
        map.setId(flightId);
        flightRepository.save(map);
    }

    public Flight delete(UUID id) {
        Flight byId = findById(id);
        byId.setActive(false);
        flightRepository.save(byId);
        return byId;
    }

    public Flight findById(UUID id) {
      return   flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("flight not found"));
    }

    public List<Flight> findAll() {
        return flightRepository.findAll();
    }
}
