package uz.jvh.uzairways.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jvh.uzairways.domain.DTO.request.AboutUsRequest;
import uz.jvh.uzairways.domain.DTO.response.AboutUsResponse;
import uz.jvh.uzairways.service.AboutUsService;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/about-us")
@RequiredArgsConstructor
public class AboutUsController {

    private final AboutUsService aboutUsService;
    
   @PostMapping("/create-about-us")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AboutUsResponse> create(@RequestBody AboutUsRequest aboutUsRequest) {
        return ResponseEntity.ok(aboutUsService.save(aboutUsRequest));
    }


    @GetMapping("/get-all")
    public ResponseEntity<List<AboutUsResponse>> getAll() {
        return ResponseEntity.ok(aboutUsService.findAll());
    }


    @GetMapping("/findBy-id{id}")
    public ResponseEntity<AboutUsResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(aboutUsService.findById(id));
    }


   @PutMapping("/update-about-us{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AboutUsResponse> update(@RequestBody AboutUsRequest aboutUsRequest, @PathVariable UUID id) {
        return ResponseEntity.ok(aboutUsService.update(aboutUsRequest, id));
    }


    @DeleteMapping("/delete{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        aboutUsService.delete(id);
        return ResponseEntity.ok("Successfully deleted about us information");
    }
}
