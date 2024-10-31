package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.jvh.uzairways.domain.entity.Booking;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.service.UserHistoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class UserHistoryController {

    private final UserHistoryService userHistoryService;

    @GetMapping("/bookings/{userId}")     // bron qilingan chiptalarin
    public List<Booking> getUserBooking(@PathVariable UUID userId) {
        return userHistoryService.getUserBooking(userId);
    }

    @GetMapping("/tickets/{userId}")
    public List<Ticket> getUserTicked(@PathVariable UUID userId) {
        return userHistoryService.getUserTicket(userId);
    }

    @GetMapping("/all-ticked/{userId}")
    public List<Ticket> getAllUserTickets(@PathVariable UUID userId) {
        return userHistoryService.getAllUserTickets(userId);
    }

    @GetMapping("/upcoming-tickets/{userId}")        //yaqinlashib kelayotgan parvozlari uchun  chiptalar
    public List<Ticket> getUpcomingUserTickets(@PathVariable UUID userId) {
        return userHistoryService.getUpcomingUserTickets(userId);
    }
}
