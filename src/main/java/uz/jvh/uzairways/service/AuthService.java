package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.DTO.request.LoginDto;
import uz.jvh.uzairways.domain.DTO.request.PasswordResetDTO;
import uz.jvh.uzairways.domain.DTO.request.UserRequest;
import uz.jvh.uzairways.domain.DTO.response.JwtResponse;
import uz.jvh.uzairways.domain.DTO.response.UserResponse;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.domain.exception.CustomException;
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
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);


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
        if (email == null || email.isEmpty()) {
            throw new CustomException("Email manzili noto'g'ri", HttpStatus.BAD_REQUEST);
        }
        logger.info("Parolni tiklash uchun email yuborish boshlandi " + email);

        User user = userRepository.findByEmail(email.toLowerCase());

                if (user == null) {
                    logger.error("Email topilmadi:", email);
                    throw  new CustomException("Email ro‘yxatdan o‘tmagan", HttpStatus.NOT_FOUND);
                };

        logger.info("Foydalanuvchi topildi:", user.getUsername());

        String resetToken = UUID.randomUUID().toString();
        user.setVerificationToken(resetToken);
        userRepository.save(user);

        String resetLink = "https://yourapp.com/reset-password?token=" + resetToken;
        String message = String.format(
                "<p>Salom, %s!</p>" +
                        "<p>Parolingizni tiklash uchun quyidagi havolani bosing:</p>" +
                        "<a href='%s'>Parolni tiklash</a>",
                user.getUsername(), resetLink);

        try {
            emailService.sendEmail(user.getEmail(), "parolni tiklash", message);
            logger.info("Parol tiklash havolasi ", user.getEmail(), "ga yuboruldi");
        } catch (RuntimeException e) {
            logger.info("Email yuborishda xatolik yuz berdi:", e.getMessage());
            throw new CustomException("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void resetPassword(PasswordResetDTO request) {
        logger.info("Parolni tiklash uchun so'rov qabul qilindi", request.getToken());

        User user = userRepository.findByVerificationToken(request.getToken())
                .orElseThrow(() -> new CustomException("Token not found", HttpStatus.NOT_FOUND));

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            logger.info("Parol va tasdiqlash kodi mos kelmadi");
            throw new CustomException("Parol va uning tasdiqlashi kodi mos kelmadi.", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setVerificationToken(null);
        userRepository.save(user);

        logger.info("Parol muvaffaqiyatli yangilandi.", user.getEmail());
    }
}
