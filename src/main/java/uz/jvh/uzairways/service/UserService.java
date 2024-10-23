package uz.jvh.uzairways.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.jvh.uzairways.Views.UserView;
import uz.jvh.uzairways.domain.DTO.request.UserCreateDTO;
import uz.jvh.uzairways.domain.DTO.response.UserResponse;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.domain.enumerators.UserRole;
import uz.jvh.uzairways.respository.UserRepository;


import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;



    public User findByUsername(String username) {
        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
        return userEntity;
    }

    @Transactional
    public String create(UserCreateDTO userCreateDTO) {
        User user = modelMapper.map(userCreateDTO, User.class);

        if(userRepository.existsByUsernameAndPassword(user.getUsername(), user.getPassword())) {
            return "This user already exists";
        };

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "success";
    }

    public Boolean checkByUsernameAndEmail(String username , String email) {
       return userRepository.existsByUsernameAndPassword(username, email);
    }


    public List<UserView> findByRole(UserRole role) {
       return userRepository.findByRole(role);
    }


    public User deleteUser(UUID userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new UsernameNotFoundException("Username " + userId + " not found"));

        user.setActive(false);
        userRepository.save(user);
        return user;
    }

    public User findById(UUID id) {
        return userRepository.findById(id).
                orElseThrow(() -> new UsernameNotFoundException("User with " + id + " not found"));
    }
}
