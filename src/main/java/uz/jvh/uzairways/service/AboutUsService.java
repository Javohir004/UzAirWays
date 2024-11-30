package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.jvh.uzairways.domain.DTO.request.AboutUsRequest;
import uz.jvh.uzairways.domain.DTO.response.AboutUsResponse;
import uz.jvh.uzairways.domain.entity.AboutUs;
import uz.jvh.uzairways.domain.exception.CustomException;
import uz.jvh.uzairways.respository.AboutUsEntityRepo;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AboutUsService {

    private final AboutUsEntityRepo repo;

    public AboutUsResponse save(AboutUsRequest aboutUsRequest) {
        if (aboutUsRequest == null) {
            throw new CustomException("AboutUsRequest must not be null",4002, HttpStatus.NOT_FOUND);
        }

        AboutUs aboutUsEntity = mapToAboutUsEntity(aboutUsRequest);
        repo.save(aboutUsEntity);
        return mapToAboutUsResponse(aboutUsEntity);
    }

    public void delete(UUID id) {
        AboutUs aboutUs = repo.findById(id)
                .orElseThrow(() -> new CustomException("AboutUs not found with id: " + id,4002, HttpStatus.NOT_FOUND));
        aboutUs.setActive(false);
        repo.save(aboutUs);
    }

    @Transactional
    public AboutUsResponse update(AboutUsRequest aboutUsRequest, UUID id) {
        AboutUs aboutUsEntity = repo.findById(id)
                .orElseThrow(() -> new CustomException("AboutUs not found with id: " + id,4002, HttpStatus.NOT_FOUND));
        aboutUsEntity.setTuri(aboutUsRequest.getTuri());
        aboutUsEntity.setTitle(aboutUsRequest.getTitle());
        aboutUsEntity.setContent(aboutUsRequest.getContent());
        repo.save(aboutUsEntity);
        return mapToAboutUsResponse(aboutUsEntity);
    }

    public List<AboutUsResponse> findAll() {
        List<AboutUs> activeAboutUs = repo.getByIsActiveTrue();
        return activeAboutUs.stream()
                .map(this::mapToAboutUsResponse)
                .collect(Collectors.toList());
    }

    public AboutUsResponse findById(UUID id) {
        AboutUs aboutUs = repo.findById(id)
                .orElseThrow(() -> new CustomException("AboutUs not found with id: " + id,4002, HttpStatus.NOT_FOUND));
        return mapToAboutUsResponse(aboutUs);
    }

    public AboutUs mapToAboutUsEntity(AboutUsRequest aboutUsRequest) {
        AboutUs aboutUs = new AboutUs();
        aboutUs.setTuri(aboutUsRequest.getTuri());
        aboutUs.setTitle(aboutUsRequest.getTitle());
        aboutUs.setContent(aboutUsRequest.getContent());
        aboutUs.setActive(true);
        return aboutUs;
    }

    public AboutUsResponse mapToAboutUsResponse(AboutUs aboutUs) {
        AboutUsResponse response = new AboutUsResponse();
        response.setId(aboutUs.getId());
        response.setTuri(aboutUs.getTuri());
        response.setTitle(aboutUs.getTitle());
        response.setContent(aboutUs.getContent());
        return response;
    }
}
