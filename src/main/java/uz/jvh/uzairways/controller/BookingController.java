package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jvh.uzairways.domain.DTO.request.CreateBookingRequest;
import uz.jvh.uzairways.domain.entity.Booking;
import uz.jvh.uzairways.service.BookingService;


import java.util.UUID;
@RequiredArgsConstructor
@RequestMapping("/api/booking")
@RestController
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/create-booking")
    public ResponseEntity<Booking> createBooking(@RequestParam UUID userId,
                                                 @RequestBody CreateBookingRequest request) {
        try {
            Booking booking = bookingService.createBooking(userId, request.getTicketIds(), request.getEmployees());
            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
