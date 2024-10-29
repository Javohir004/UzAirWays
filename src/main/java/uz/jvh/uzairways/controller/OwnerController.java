package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jvh.uzairways.Views.UserView;
import uz.jvh.uzairways.domain.DTO.request.UserCreateDTO;
import uz.jvh.uzairways.domain.enumerators.UserRole;
import uz.jvh.uzairways.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final UserService userService;


    // Barcha foydalanuvchilarni ko'rish
    @GetMapping
    public ResponseEntity<List<UserView>> getAllUsers() {
        List<UserView> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    // Rol bo'yicha foydalanuvchilarni ko'rish
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserView>> getUsersByRole(@PathVariable UserRole role) {
        List<UserView> users = userService.findByRole(role);
        return ResponseEntity.ok(users);
    }

    // Foydalanuvchi yaratish
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        return ResponseEntity.ok(userService.create(userCreateDTO));
    }

    // Foydalanuvchini o'chirish

    /**
     *
     * hello
     * @param userId
     * @return User o'chadi
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("Foydalanuvchi muvaffaqiyatli o'chirildi.");
    }

    // Foydalanuvchini yangilash
    @PutMapping("/{userId}")
    public ResponseEntity<UserView> updateUser(@PathVariable UUID userId, @RequestBody UserCreateDTO updatedUser) {
        UserView updatedUserInfo = userService.updateUser(updatedUser, userId);
        return ResponseEntity.ok(updatedUserInfo);
    }

    // ID bo'yicha foydalanuvchini topish
    @GetMapping("/{userId}")
    public ResponseEntity<UserView> getUserById(@PathVariable UUID userId) {
        UserView user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }
}
