package uz.jvh.uzairways.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.DTO.request.AirPlaneDTO;
import uz.jvh.uzairways.domain.DTO.response.AirPlaneResponse;
import uz.jvh.uzairways.domain.entity.AirPlane;
import uz.jvh.uzairways.respository.AirPlaneRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AirPlaneService {


    private final AirPlaneRepository airPlaneRepository;


    @Transactional
    public String create(AirPlaneDTO airPlane) {
        AirPlane save = mapToEntity(airPlane);
        airPlaneRepository.save(save);
        return "success";
    }

    public void delete(UUID id) {
        AirPlane byId = findById(id);
        byId.setActive(false);
        airPlaneRepository.save(byId);
    }

    @Transactional
    public void update(AirPlaneDTO airPlane , UUID id) {
        AirPlane airPlane1 = mapToEntity(airPlane);
        airPlane1.setId(id);
        airPlaneRepository.save(airPlane1);
    }

    public AirPlane findById(UUID id) {
        return airPlaneRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("AirPlane with ID " + id + " not found"));
    }

    public AirPlane mapToEntity(AirPlaneDTO airPlaneDTO) {
        AirPlane airPlane = new AirPlane();
        airPlane.setId(airPlane.getId());
        airPlane.setAircraftType(airPlaneDTO.getAircraftType());
        airPlane.setModel(airPlaneDTO.getModel());
        airPlane.setManufacturer(airPlaneDTO.getManufacturer());
        return airPlane;
    }


    public AirPlaneResponse mapToResponse(AirPlane airPlane) {
        AirPlaneResponse response = new AirPlaneResponse();
        response.setId(airPlane.getId());
//        response.setId(UUID.fromString(airPlane.getId().toString()));
        response.setAircraftType(String.valueOf(airPlane.getAircraftType()));
        response.setModel(airPlane.getModel());
        response.setManufacturer(airPlane.getManufacturer());
        return response;
    }


    public List<AirPlaneResponse> findAll() {
        List<AirPlane> all = airPlaneRepository.findAllByIsActiveTrue();
        return all.stream()
                .map(this::mapToResponse)
                .toList();
    }


}
