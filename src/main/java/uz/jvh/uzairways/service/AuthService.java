package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.DTO.request.LoginDto;
import uz.jvh.uzairways.domain.DTO.request.PasswordResetDTO;
import uz.jvh.uzairways.domain.DTO.request.UserRequest;
import uz.jvh.uzairways.domain.DTO.response.JwtResponse;
import uz.jvh.uzairways.domain.DTO.response.UserResponse;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.respository.UserRepository;
import uz.jvh.uzairways.security.JwtTokenUtil;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final EmailService emailService;


    public UserResponse save(UserRequest user) {
        User user1 = userService.mapRequestToEntity(user);
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user1);
        return userService.mapEntityToResponse(user1);
    }

    public JwtResponse login(LoginDto loginDto) {
        User username = userService.findByUsername(loginDto.getUsername());
        if (!passwordEncoder.matches(loginDto.getPassword(), username.getPassword())) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new JwtResponse(jwtTokenUtil.generateToken(loginDto.getUsername()));

    }

    public void sendPasswordResetEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email not found"));

        String resetToken = UUID.randomUUID().toString();
        user.setVerificationToken(resetToken);
        userRepository.save(user);

        String resetLink = "https://yourapp.com/reset-password?token=" + resetToken;
        String message = String.format(
                "<p>Salom, %s!</p>" +
                        "<p>Parolingizni tiklash uchun quyidagi havolani bosing:</p>" +
                        "<a href='%s'>Parolni tiklash</a>",
                user.getUsername(), resetLink);

        emailService.sendEmail(user.getEmail(), "parolni tiklash", message);
    }

    public void resetPassword(PasswordResetDTO request) {
        User user = userRepository.findByVerificationToken(request.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Token not found"));

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Parol va uning tasdiqlashi kodi mos kelmadi.");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setVerificationToken(null);
        userRepository.save(user);
    }
}
