package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jvh.uzairways.domain.DTO.request.LoginDto;
import uz.jvh.uzairways.domain.DTO.request.PasswordResetDTO;
import uz.jvh.uzairways.domain.DTO.request.UserRequest;
import uz.jvh.uzairways.domain.DTO.response.JwtResponse;
import uz.jvh.uzairways.domain.DTO.response.UserResponse;
import uz.jvh.uzairways.service.AuthService;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest userRequest) {
        return authService.save(userRequest);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/sent-email")
    public ResponseEntity<String> resetPassword(@RequestBody String email) {
        authService.sendPasswordResetEmail(email);
        return ResponseEntity.ok("Parolni tiklash uchun emailga xabar yuborildi");
    }

    @PostMapping("/set-new-password")
    public ResponseEntity<String> setNewPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        authService.resetPassword(passwordResetDTO);
        return ResponseEntity.ok("Parol muvaffaqiyatli yangilandi.");
    }

}
