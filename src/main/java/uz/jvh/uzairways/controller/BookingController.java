package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jvh.uzairways.domain.DTO.request.CreateBookingRequest;
import uz.jvh.uzairways.domain.DTO.response.TickedResponse;
import uz.jvh.uzairways.domain.entity.Booking;
import uz.jvh.uzairways.service.BookingService;

import java.util.ArrayList;
import java.util.List;

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


    @GetMapping("/get-tickets-flight-by-userId")
    public ResponseEntity<List<TickedResponse>> getTicketsByUserId(@RequestParam UUID userId) {
        return ResponseEntity.ok(bookingService.getBookingsByOwnerId(userId));
    }

    // Muddati o'tgan ticketlarni olish
    @GetMapping("/get-expired-tickets")
    public ResponseEntity<List<TickedResponse>> getExpiredTickets(@RequestParam UUID userId) {
        List<TickedResponse> expiredTickets = bookingService.getExpiredTicketsByUserId(userId);
        return ResponseEntity.ok(expiredTickets);
    }

    // Muddati o'tmagan ticketlarni olish
    @GetMapping("/get-active-tickets")
    public ResponseEntity<List<TickedResponse>> getActiveTickets(@RequestParam UUID userId) {
        List<TickedResponse> activeTickets = bookingService.getActiveTicketsByUserId(userId);
        return ResponseEntity.ok(activeTickets);
    }






}
