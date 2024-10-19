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
import uz.jvh.uzairways.domain.DTO.request.UserCreateDTO;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.respository.UserRepository;


import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private JavaMailSender mailSender;



    public User findByUsername(String username) {
        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
        return userEntity;
    }

    @Transactional
    public String create(UserCreateDTO userCreateDTO) {
        User user = modelMapper.map(userCreateDTO, User.class);

        if(userRepository.checkByUsernameAndPassword(user.getUsername(), user.getPassword())) {
            return "This user already exists";
        };

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "success";
    }



    public Boolean checkByUsernameAndEmail(String username , String email) {
       return userRepository.CheckByUsernameAndEmail(username, email);
    }







}
