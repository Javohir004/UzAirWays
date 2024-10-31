package uz.jvh.uzairways.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jvh.uzairways.Views.UserView;
import uz.jvh.uzairways.domain.DTO.request.UserRequest;
import uz.jvh.uzairways.domain.DTO.response.UserResponse;
import uz.jvh.uzairways.domain.enumerators.UserRole;
import uz.jvh.uzairways.service.AuthService;
import uz.jvh.uzairways.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final UserService userService;
    private final AuthService authService;

    // Barcha foydalanuvchilarni ko'rish
    @GetMapping("/get-all-user")
    public ResponseEntity<List<UserView>> getAllUsers() {
        List<UserView> users = userService.findAllJ();
        return ResponseEntity.ok(users);
    }

    // Rol bo'yicha foydalanuvchilarni ko'rish
    @GetMapping("/find-by-role/{role}")
    public ResponseEntity<List<UserView>> getUsersByRole(@PathVariable UserRole role) {
        List<UserView> users = userService.findByRole(role);
        return ResponseEntity.ok(users);
    }

    // Foydalanuvchi yaratish
    @PostMapping("create-user")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userCreateDTO) {
        return ResponseEntity.ok(authService.save(userCreateDTO));
    }

    // Foydalanuvchini o'chirish
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("Foydalanuvchi muvaffaqiyatli o'chirildi.");
    }

    // Foydalanuvchini yangilash
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserView> updateUser(@PathVariable UUID userId, @RequestBody UserRequest updatedUser) {
        UserView updatedUserInfo = userService.updateUserJ(updatedUser, userId);
        return ResponseEntity.ok(updatedUserInfo);
    }

    // ID bo'yicha foydalanuvchini topish
    @GetMapping("/find-by-id/{userId}")
    public ResponseEntity<UserView> getUserById(@PathVariable String userId) {
        UserView user = userService.findById(UUID.fromString("616c5a6f-f661-458b-bf3d-498c07d9f37c"));
        return ResponseEntity.ok(user);
    }


}
