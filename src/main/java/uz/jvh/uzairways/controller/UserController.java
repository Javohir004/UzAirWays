package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import uz.jvh.uzairways.domain.DTO.request.UserRequest;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.service.UserService;

import java.util.UUID;


@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable("id") UUID id, @RequestBody UserRequest userRequest) {
        return userService.update(id, userRequest);
    }

}
