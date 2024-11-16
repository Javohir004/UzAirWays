package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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


}
