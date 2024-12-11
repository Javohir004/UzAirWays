package uz.jvh.uzairways.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.jvh.uzairways.domain.DTO.request.AirPlaneDTO;
import uz.jvh.uzairways.domain.DTO.response.AirPlaneResponse;
import uz.jvh.uzairways.domain.entity.AirPlane;
import uz.jvh.uzairways.domain.exception.CustomException;
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

    @Transactional
    public void delete(UUID id) {
        AirPlane airPlane = airPlaneRepository.findById(id).orElseThrow(NoSuchElementException::new);
        airPlane.setActive(false);
        airPlaneRepository.save(airPlane);
    }

    @Transactional
    public void update(AirPlaneDTO airPlane , UUID id) {
        AirPlane airPlane1 = mapToEntity(airPlane);
        airPlane1.setId(id);
        airPlaneRepository.save(airPlane1);
    }

    public AirPlaneResponse findById(UUID id) {
        AirPlane airPlane = airPlaneRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomException("AirPlane with ID " + id + " not found",4002, HttpStatus.NOT_FOUND));
        return mapToResponse(airPlane);
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
        response.setAircraftType(String.valueOf(airPlane.getAircraftType()));
        response.setModel(airPlane.getModel());
        response.setManufacturer(airPlane.getManufacturer());
        return response;
    }

    public List<AirPlaneResponse> findAll() {
        List<AirPlane> all = airPlaneRepository.findAllByIsActiveTrueOrderByCreatedDesc();
        return all.stream()
                .map(this::mapToResponse)
                .toList();
    }

}
