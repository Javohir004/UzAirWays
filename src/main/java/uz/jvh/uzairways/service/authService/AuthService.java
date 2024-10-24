package uz.jvh.uzairways.service.authService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.DTO.request.LoginDto;
import uz.jvh.uzairways.domain.DTO.response.JwtResponse;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.service.UserService;


@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public JwtResponse login(LoginDto loginDto) {
        User user = userService.findByUsername(loginDto.getUsername());
        if(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return new JwtResponse(jwtService.generateToken(user));
        }
        throw new UsernameNotFoundException("Wrong username or password");
    }

}
