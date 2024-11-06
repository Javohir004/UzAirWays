package uz.jvh.uzairways.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jvh.uzairways.domain.DTO.request.ByTickedRequest;
import uz.jvh.uzairways.domain.DTO.request.CreateTicketRequest;
import uz.jvh.uzairways.domain.DTO.request.TicketDTO;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.domain.enumerators.AircraftType;
import uz.jvh.uzairways.service.TicketService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;


    @GetMapping("/get-all")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/find-byId{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable UUID id) {
        Ticket ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }


    @PutMapping("/update{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable UUID id, @RequestBody Ticket ticket) {
        Ticket updatedTicket = ticketService.updateTicket(id, ticket);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/delete{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable UUID id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

//    @DeleteMapping("/cancel-ticked/{id}")
//    public ResponseEntity<String> cancelTicket(@PathVariable UUID id) {
//        try {
//            ticketService.cancelTicked(id);
//            return ResponseEntity.ok("Ticket successfully cancelled");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @PostMapping("/get-flight-info")
    public ResponseEntity<List<Ticket>> getFlightInfo(@RequestBody ByTickedRequest request) {
        List<Ticket> flightInfo = ticketService.getFlightInfo(request);
        return ResponseEntity.ok(flightInfo);
    }



}
