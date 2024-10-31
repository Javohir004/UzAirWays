package uz.jvh.uzairways.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.domain.DTO.request.TicketDTO;
import uz.jvh.uzairways.domain.entity.Ticket;
import uz.jvh.uzairways.domain.enumerators.ClassType;
import uz.jvh.uzairways.respository.TicketRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;


    /// miyyanga ... o'zincha o'chirib tashlama yoqmasa tegma kommentga olib qo'y


    // Barcha chiptalarni olish
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // ID bo'yicha chipta olish
    public Ticket getTicketById(UUID id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chipta topilmadi: " + id));
    }

    // Yangi chipta yaratish
    public Ticket createTicket(TicketDTO ticket) {
        Ticket ticket1 = mapRequestToTicket(ticket);
        return ticketRepository.save(ticket1);
    }


        // Ticket yaratish
        public List<Ticket> createTickets(TicketDTO ticketDTO, String aircraftType) {
            List<Ticket> tickets = new ArrayList<>();

            // O'rindiq turlariga mos narxlar
            Float businessClassPrice = 1000F;
            Float firstClassPrice = 500F;
            Float economyClassPrice = 200F;

            // Samolyot turi asosida o'rindiq va narxlarni belgilash
            if ("jet".equalsIgnoreCase(aircraftType)) {
                // Jetda mavjud o'rindiqlar
                int[] availableSeats = {20, 10, 30}; // Business, First Class, Economy

                // Business sinfi
                for (int j = 0; j < availableSeats[0]; j++) {
                    Ticket ticket = new Ticket();
                    ticket.setFlight(ticketDTO.getFlight());
                    ticket.setOwner(ticketDTO.getOwner());
                    ticket.setPrice(businessClassPrice);
                    ticket.setClassType(ClassType.BUSINESS);
                    tickets.add(ticket);
                }
                // Birinchi sinf
                for (int j = 0; j < availableSeats[1]; j++) {
                    Ticket ticket = new Ticket();
                    ticket.setFlight(ticketDTO.getFlight());
                    ticket.setOwner(ticketDTO.getOwner());
                    ticket.setPrice(firstClassPrice);
                    ticket.setClassType(ClassType.FIRST);
                    tickets.add(ticket);
                }
                // Iqtisodiy sinf
                for (int j = 0; j < availableSeats[2]; j++) {
                    Ticket ticket = new Ticket();
                    ticket.setFlight(ticketDTO.getFlight());
                    ticket.setOwner(ticketDTO.getOwner());
                    ticket.setPrice(economyClassPrice);
                    ticket.setClassType(ClassType.ECONOMY);
                    tickets.add(ticket);
                }
            } else if ("propeller".equalsIgnoreCase(aircraftType)) {
                // Propellerda mavjud o'rindiqlar
                int[] availableSeats = {40, 20, 60}; // Business, First Class, Economy

                // Business sinfi
                for (int j = 0; j < availableSeats[0]; j++) {
                    Ticket ticket = new Ticket();
                    ticket.setFlight(ticketDTO.getFlight());
                    ticket.setOwner(ticketDTO.getOwner());
                    ticket.setPrice(businessClassPrice); // 20% chegirma
                    ticket.setClassType(ClassType.BUSINESS);
                    tickets.add(ticket);
                }
                // Birinchi sinf
                for (int j = 0; j < availableSeats[1]; j++) {
                    Ticket ticket = new Ticket();
                    ticket.setFlight(ticketDTO.getFlight());
                    ticket.setOwner(ticketDTO.getOwner());
                    ticket.setPrice(firstClassPrice); // 20% chegirma
                    ticket.setClassType(ClassType.FIRST);
                    tickets.add(ticket);
                }
                // Iqtisodiy sinf
                for (int j = 0; j < availableSeats[2]; j++) {
                    Ticket ticket = new Ticket();
                    ticket.setFlight(ticketDTO.getFlight());
                    ticket.setOwner(ticketDTO.getOwner());
                    ticket.setPrice(economyClassPrice); // 25% chegirma
                    ticket.setClassType(ClassType.ECONOMY);
                    tickets.add(ticket);
                }
            }

            // Chiptalarni saqlash
            return ticketRepository.saveAll(tickets);
        }



    // Chipta ma'lumotlarini yangilash
    public Ticket updateTicket(UUID id, Ticket ticket) {
        Ticket existingTicket = getTicketById(id);
        existingTicket.setId(id);
        existingTicket.setFlight(ticket.getFlight());
        existingTicket.setTicketStatus(ticket.getTicketStatus());
        existingTicket.setSeatNumber(ticket.getSeatNumber());
        existingTicket.setNearWindow(ticket.getNearWindow());
        existingTicket.setOwner(ticket.getOwner());
        existingTicket.setPrice(ticket.getPrice());
        existingTicket.setClassType(ticket.getClassType());
        // Boshqa maydonlarni yangilang
        return ticketRepository.save(existingTicket);
    }

    // Chipta o'chirish
    public void deleteTicket(UUID id) {
        Ticket existingTicket = getTicketById(id);
        existingTicket.setActive(false);
        ticketRepository.save(existingTicket);
    }

    public Ticket mapRequestToTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setTicketStatus(ticketDTO.getTicketStatus());
        ticket.setSeatNumber(ticketDTO.getSeatNumber());
        ticket.setTicketStatus(ticketDTO.getTicketStatus());
        ticket.setOwner(ticketDTO.getOwner());
        ticket.setPrice(ticketDTO.getPrice());
        ticket.setClassType(ticketDTO.getClassType());
        ticket.setFlight(ticketDTO.getFlight());
        ticket.setBookingDate(ticketDTO.getBookingDate());
        return ticket;
    }

}
