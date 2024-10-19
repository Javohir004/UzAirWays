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
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(userRepository.existsByEnabledTrueAndUsernameOrEmail(
                userCreateDTO.getEmail(), userCreateDTO.getUsername())) {
            return "email already exists";
        }
        String verificationToken = UUID.randomUUID().toString();

        user.setVerificationToken(verificationToken);
        user = userRepository.save(user);

        String verificationLink = "http://localhost:8088/auth/verification?token=" + verificationToken;
        sendVerificationEmail(user.getEmail(), verificationLink);
        return "success";
    }


    public String verification(String token) {

        User user = userRepository.findUserByVerificationToken(token).orElseThrow(
                () -> new UsernameNotFoundException("Username with " + token + " not found"));
        if (user != null) {
            user.setEnabled(true);
            userRepository.save(user);
            return "User verified successfully";
        }

            return "Invalid verification token";
    }

    public void sendVerificationEmail(String to, String verificationLink) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject("Email Verification");

            String content = "<p>Please click the link below to verify your email:</p>" +
                    "<a href=\"" + verificationLink + "\">Verify</a>";

            helper.setText(content, true);  // 'true' indicates that the content is HTML

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public Boolean checkByUsernameAndEmail(String username, String email) {
       return userRepository.CheckByUsernameAndEmail(username, email);
    }

}
