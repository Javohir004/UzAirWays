package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.jvh.uzairways.domain.DTO.request.BookingRequest;
import uz.jvh.uzairways.domain.entity.Booking;
import uz.jvh.uzairways.service.BookingService;

@RequiredArgsConstructor
@RequestMapping("/api/booking")
@RestController
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/create-booking")
    public Booking createBooking(@RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }
}
