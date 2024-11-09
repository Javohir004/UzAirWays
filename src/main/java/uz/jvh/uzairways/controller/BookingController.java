package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.jvh.uzairways.domain.DTO.request.ByTickedRequest;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.service.BookingService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/booking")
@RestController
public class BookingController {

    private final BookingService bookingService;


    @PostMapping("/user-reserve-tickets")
    public ResponseEntity<String> reserveTickets(@RequestBody List<Ticket> tickets,
                                                 @RequestBody ByTickedRequest request) {
        try {
            bookingService.bronTicked(tickets, request);
            return ResponseEntity.ok("Tickets successfully reserved.");
        } catch (Exception e) {
            return ResponseEntity.ok("Failed to reserve tickets");
        }
    }
}
