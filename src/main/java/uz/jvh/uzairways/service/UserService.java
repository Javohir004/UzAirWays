package uz.jvh.uzairways.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.Views.UserView;
import uz.jvh.uzairways.domain.DTO.request.UserRequest;
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
    private final ModelMapper modelMapper;

    public void save(User user) {
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
        return userEntity;
    }

//    public Boolean checkByUsernameAndEmail(String username, String email) {
//        return userRepository.existsByUsernameAndPassword(username, email);
//    }


    public List<UserView> findByRole(UserRole role) {
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
    public UserView updateUserJ(UserRequest userCreateDTO , UUID userId) {
        User user = modelMapper.map(userCreateDTO, User.class);
        user.setId(userId);
        userRepository.save(user);
        return modelMapper.map(user, UserView.class);
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
                .build();

    }

    public UserResponse mapEntityToResponse(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .surname(user.getSurname())
                .role(user.getRole())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .phoneNumber(user.getPhoneNumber())
                .enabled(user.isEnabled())
                .address(user.getAddress())
                .createDate(user.getCreated().toLocalDate())
                .build();
    }

    public UserView findById(UUID id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new UsernameNotFoundException("User with " + id + " not found"));
        UserView map = modelMapper.map(user, UserView.class);
        return map;
    }


    public List<UserView> findAllJ() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserView.class))
                .collect(Collectors.toList());
    }

}
