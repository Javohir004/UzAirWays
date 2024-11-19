package uz.jvh.uzairways.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.Views.UserView;
import uz.jvh.uzairways.domain.DTO.request.UserRequest;;
import uz.jvh.uzairways.domain.DTO.response.UserResponse;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.domain.enumerators.UserRole;
import uz.jvh.uzairways.respository.UserRepository;

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
        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
        return userEntity;
    }


    public List<User> findByRole(UserRole role) {
        return userRepository.findByRoleAndIsActiveTrue(role);
    }


    public User deleteUser(UUID userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new UsernameNotFoundException("Username " + userId + " not found"));

        user.setActive(false);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public UserResponse updateUserJ(UserRequest userCreateDTO, UUID userId) {
        User user = mapRequestToEntity(userCreateDTO);
        user.setId(userId);
        userRepository.save(user);
        return mapEntityToResponse(user);
    }

    public User findByIdJ(UUID id) {
        return userRepository.findById(id).
                orElseThrow(() -> new UsernameNotFoundException("User  not found"));
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

    public UserView findById(UUID id) {
        return (UserView) userRepository.findById(id).
                orElseThrow(() -> new UsernameNotFoundException("User with " + id + " not found"));

    }


    public List<User> findAllJ() {
        List<User> allUsers = userRepository.findAllByIsActiveTrue();

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
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User  not found"));
        return user.getBalance();
    }

}
