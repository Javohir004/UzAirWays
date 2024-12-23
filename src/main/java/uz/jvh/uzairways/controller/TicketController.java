package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.jvh.uzairways.domain.DTO.request.ByTickedRequest;
import uz.jvh.uzairways.domain.DTO.request.TicketDTO;
import uz.jvh.uzairways.domain.DTO.response.TicketResponse;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.service.TicketService;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/get-all")
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        List<TicketResponse> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/find-by-id{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable UUID id) {
        TicketResponse ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

   // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update{id}")
    public ResponseEntity<TicketResponse> updateTicket(@PathVariable UUID id, @RequestBody TicketDTO ticket) {
        TicketResponse updatedTicket = ticketService.updateTicket(id, ticket);
        return ResponseEntity.ok(updatedTicket);
    }

  //  @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable UUID id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok("Foydalanuvchi muvaffaqiyatli o'chirildi");
    }

  //  @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/get-flight-info")
    public ResponseEntity<List<TicketResponse>> getFlightInfo(@RequestBody ByTickedRequest request) {
        List<TicketResponse> flightInfo = ticketService.getFlightInfo(request);
        return ResponseEntity.ok(flightInfo);
    }
}
