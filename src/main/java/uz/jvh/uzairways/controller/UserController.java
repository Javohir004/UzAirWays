package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jvh.uzairways.domain.DTO.request.UserRequest;
import uz.jvh.uzairways.domain.DTO.response.UserResponse;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.domain.enumerators.UserRole;
import uz.jvh.uzairways.service.AuthService;
import uz.jvh.uzairways.service.UserService;

import java.util.List;
import java.util.UUID;


@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable("id") UUID id, @RequestBody UserRequest userRequest) {
        return userService.update(id, userRequest);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-user")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllJ();
        return ResponseEntity.ok(users);
    }

//    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/User-role/{role}/owner")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable UserRole role) {
        List<User> users = userService.findByRole(role);
        return ResponseEntity.ok(users);
    }

//    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/create-admin")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userCreateDTO) {
        return ResponseEntity.ok(authService.save(userCreateDTO));
    }

//    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("Foydalanuvchi muvaffaqiyatli o'chirildi.");
    }

//    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    @GetMapping("/find-by-id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        User user = userService.findByIdJ(userId);
        return ResponseEntity.ok(user);
    }

//    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-balance")
    public ResponseEntity<Double> getUserBalance(@RequestParam UUID userId) {
        Double userBalance = userService.getUserBalance(userId);
        return ResponseEntity.ok(userBalance);
    }

    //    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add-balance")
    public ResponseEntity<Double> addUserBalance(@RequestParam UUID userId , @RequestParam Double balance) {
        Double userBalance = userService.AddBalance(userId, balance);
        return ResponseEntity.ok(userBalance);
    }
}
