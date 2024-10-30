package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.jvh.uzairways.domain.DTO.response.UserResponse;
import uz.jvh.uzairways.service.UserService;

import java.util.UUID;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/me")
    public UserResponse getProfileById(@RequestParam UUID UserId) {
        return userService.getProfile(UserId);
    }
}
