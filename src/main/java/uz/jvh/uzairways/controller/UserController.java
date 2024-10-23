package uz.jvh.uzairways.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.jvh.uzairways.Views.UserView;
import uz.jvh.uzairways.domain.DTO.request.UserCreateDTO;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.domain.enumerators.UserRole;
import uz.jvh.uzairways.service.UserService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/return")
    public String index() {
        return "owner-page";
    }

    @GetMapping("/create-user")
    public String createUser() {
        return "create-user";
    }



    @GetMapping("/show-users-by-role")
    public String getUsersByRole(@RequestParam("role") UserRole role, Model model) {

        List<UserView> users = userService.findByRole(role);
        model.addAttribute("users", users);
        return "show-users";

    }


//    @PostMapping("/show-users-by-role")
//    public String getUsers(Model model , UserRole userRole) {
//        model.getAttribute("users");
//        return "show-users";
//    }


    @PostMapping("/create-user")
    public String createUser(UserCreateDTO userCreateDTO) {
        userService.create(userCreateDTO);
        return "owner-page";
    }


    @DeleteMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") UUID userId, Model model) {
        User user = userService.deleteUser(userId);
        List<UserView> users = userService.findByRole(user.getRole());
        model.addAttribute("users", users);
        return "owner-page";
    }



}
