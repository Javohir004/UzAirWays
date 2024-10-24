package uz.jvh.uzairways.controller.homeAndAuth;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.jvh.uzairways.domain.DTO.request.LoginDto;
import uz.jvh.uzairways.domain.DTO.request.UserCreateDTO;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.domain.enumerators.UserRole;
import uz.jvh.uzairways.service.authService.AuthService;
import uz.jvh.uzairways.service.UserService;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    @Autowired
    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "sign-in-page";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "sign-up-page";
    }

    @PostMapping("/register")
    public String register(UserCreateDTO user, Model model) {
        if (userService.checkByUsernameAndEmail(user.getUsername(), user.getEmail())) {
            model.addAttribute("message", "This email or username is already in use!");
            return "sign-up-page";
        }
        userService.create(user);
        return "home-page";
    }

    @PostMapping("/login")
    public String login(LoginDto loginDto) {
        authService.login(loginDto);
        User user = userService.findByUsername(loginDto.getUsername());
        if(user.getRole() == UserRole.OWNER){
            return "owner-page";
        }else if(user.getRole() == UserRole.ADMIN){
            return "admin-page";
        }
        return "home-page";
    }
}
