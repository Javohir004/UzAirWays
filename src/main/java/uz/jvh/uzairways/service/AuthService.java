package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.jvh.uzairways.domain.DTO.request.LoginDto;
import uz.jvh.uzairways.domain.DTO.request.UserRequest;
import uz.jvh.uzairways.domain.DTO.response.JwtResponse;
import uz.jvh.uzairways.domain.DTO.response.UserResponse;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.security.JwtTokenUtil;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

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
       return  new JwtResponse(jwtTokenUtil.generateToken(String.valueOf(username)));

    }
}
