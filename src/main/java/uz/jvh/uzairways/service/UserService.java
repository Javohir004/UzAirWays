package uz.jvh.uzairways.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.Views.UserView;
import uz.jvh.uzairways.domain.DTO.request.UserRequest;
import uz.jvh.uzairways.domain.DTO.response.TickedResponse;
import uz.jvh.uzairways.domain.DTO.response.UserResponse;
import uz.jvh.uzairways.domain.entity.Flight;
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

    public Boolean checkByUsernameAndEmail(String username, String email) {
        return userRepository.existsByUsernameAndPassword(username, email);
    }


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


    public User findById(UUID id) {
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

    public UserResponse getProfile(UUID userId) {
        User user = findById(userId);

        List<TickedResponse> ticketHistory = user.getTickets().stream().map(ticket -> {
            TickedResponse tickedResponse = new TickedResponse();
            tickedResponse.setSeatNumber(ticket.getSeatNumber());
            tickedResponse.setPrice(ticket.getPrice());
            tickedResponse.setBookingDate(ticket.getBookingDate());
            tickedResponse.setClassType(ticket.getClassType().toString());
            tickedResponse.setNearWindow(ticket.getNearWindow());
            tickedResponse.setTicketStatus(ticket.getTicketStatus().toString());

            Flight flight = ticket.getFlight();
            tickedResponse.setFlightNumber(flight.getFlightNumber());
            tickedResponse.setDepartureTime(flight.getDepartureTime());
            tickedResponse.setArrivalTime(flight.getArrivalTime());
            tickedResponse.setDepartureAirport(flight.getDepartureAirport());
            tickedResponse.setArrivalAirport(flight.getArrivalAirport());
            tickedResponse.setFlightStatus(flight.getStatus().toString());
            return tickedResponse;

        }).toList();
        UserResponse userResponse = mapEntityToResponse(user);
        userResponse.setTicketHistory(ticketHistory);
        return userResponse;
    }
}
