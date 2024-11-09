
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

@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final UserService userService;
    private final AuthService authService;

    // Barcha foydalanuvchilarni ko'rish
    @GetMapping("/all-user")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.findAllforOwner();
        return ResponseEntity.ok(users);
    }

    // Rol bo'yicha foydalanuvchilarni ko'rish
    @GetMapping("/User-role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable UserRole role) {
        List<User> users = userService.findByRole(role);
        return ResponseEntity.ok(users);
    }

    // Foydalanuvchi yaratish
    @PostMapping("/create-admin")
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
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID userId, @RequestBody UserRequest updatedUser) {
        UserResponse updatedUserInfo = userService.updateUserJ(updatedUser, userId);
        return ResponseEntity.ok(updatedUserInfo);
    }

    // ID bo'yicha foydalanuvchini topish
    @GetMapping("/find-by-id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        User user = userService.findByIdJ(userId);
        return ResponseEntity.ok(user);
    }


}