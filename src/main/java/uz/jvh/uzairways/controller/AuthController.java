package uz.jvh.uzairways.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.jvh.uzairways.domain.DTO.request.LoginDto;
import uz.jvh.uzairways.domain.DTO.request.UserCreateDTO;
import uz.jvh.uzairways.service.AuthService;
import uz.jvh.uzairways.service.UserService;


@Controller("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }


    @RequestMapping("/register")
    public String registerPage() {
        return "register";
    }


    @PostMapping("/register")
    public String register(@RequestBody UserCreateDTO user, Model model) {
        if(userService.checkByUsernameAndEmail(user.getUsername(), user.getEmail())) {
            model.addAttribute("message", "This email or username is already in use!");

            return "register";
        }
        return userService.create(user);
    }


    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        authService.login(loginDto);
        return "login";
    }

}
