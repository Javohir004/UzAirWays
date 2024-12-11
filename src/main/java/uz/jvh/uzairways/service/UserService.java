package uz.jvh.uzairways.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.DTO.request.UserRequest;;
import uz.jvh.uzairways.domain.DTO.response.UserResponse;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.domain.enumerators.UserRole;
import uz.jvh.uzairways.domain.exception.CustomException;
import uz.jvh.uzairways.respository.UserRepository;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(User user) {
        userRepository.save(user);
    }


    public User findByUsername(String username) {
        User userEntity = userRepository.findByUsernameAndIsActiveTrue(username)
                .orElseThrow(() -> new CustomException("Username " + username + " not found", 4002, HttpStatus.NOT_FOUND));
        return userEntity;
    }


    public List<User> findByRole(UserRole role) {
        return userRepository.findByRoleAndIsActiveTrueOrderByCreatedDesc(role);
    }


    public User deleteUser(UUID userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new CustomException("Username  not found", 4002, HttpStatus.NOT_FOUND));

        user.setActive(false);
        userRepository.save(user);
        return user;
    }


    public User findByIdJ(UUID id) {
        return userRepository.findById(id).
                orElseThrow(() -> new CustomException("User  not found",4002, HttpStatus.NOT_FOUND));
    }


    public User mapRequestToEntity(UserRequest userRequest) {
        return User.builder()
                .username(userRequest.getUsername())
                .surname(userRequest.getSurname())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(userRequest.getRole())
                .email(userRequest.getEmail())
                .birthDate(userRequest.getBirthDate())
                .phoneNumber(userRequest.getPhoneNumber())
                .balance(userRequest.getBalance())
                .address(userRequest.getAddress())
                .passportSeries(userRequest.getPassportSeries())
                .build();

    }


    public UserResponse mapEntityToResponse(User user) {
        return UserResponse.builder()
                .uuid(user.getId())
                .username(user.getUsername())
                .surname(user.getSurname())
                .role(user.getRole())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .createDate(user.getCreated().toLocalDate())
                .passportSeries(user.getPassportSeries())
                .build();
    }

    public List<User> findAllJ() {
        List<User> allUsers = userRepository.findAllByIsActiveTrueOrderByCreatedDesc();

        return allUsers.stream()
                .filter(user -> user.getRole().equals(UserRole.USER))
                .collect(Collectors.toList());
    }

    public User update(UUID id, UserRequest userRequest) {
        User user = findByIdJ(id);
        user.setUsername(userRequest.getUsername() != null ? userRequest.getUsername() : user.getUsername());
        user.setSurname(userRequest.getSurname() != null ? userRequest.getSurname() : user.getSurname());
        user.setPassword(userRequest.getPassword() != null ? userRequest.getPassword() : user.getPassword());
        user.setEmail(userRequest.getEmail() != null ? userRequest.getEmail() : user.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber() != null ? userRequest.getPhoneNumber() : user.getPhoneNumber());
        user.setRole(userRequest.getRole() != null ? userRequest.getRole() : user.getRole());
        user.setBirthDate(userRequest.getBirthDate() != null ? userRequest.getBirthDate() : user.getBirthDate());
        user.setAddress(userRequest.getAddress() != null ? userRequest.getAddress() : user.getAddress());
        user.setPassportSeries(userRequest.getPassportSeries() != null ? userRequest.getPassportSeries() : user.getPassportSeries());
        return userRepository.save(user);
    }

    public Double getUserBalance(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("User  not found", 4002, HttpStatus.NOT_FOUND));
        return user.getBalance();
    }

}
