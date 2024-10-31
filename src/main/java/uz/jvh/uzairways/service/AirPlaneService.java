package uz.jvh.uzairways.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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


    private AirPlaneRepository airPlaneRepository;


    private ModelMapper modelMapper;

    @Transactional
    public String create(AirPlaneDTO airPlane) {
        AirPlane save = airPlaneRepository.save(modelMapper.map(airPlane, AirPlane.class));
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
        AirPlane map = modelMapper.map(airPlane, AirPlane.class);
        map.setId(id);
        airPlaneRepository.save(map);
    }

    public AirPlane findById(UUID id) {
        return airPlaneRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("AirPlane with ID " + id + " not found"));
    }


    public List<AirPlaneResponse> findAll() {
        List<AirPlane> all = airPlaneRepository.findAll();
        return all.stream()
                .map(airPlane -> modelMapper.map(airPlane, AirPlaneResponse.class))
                .toList();
    }
}
