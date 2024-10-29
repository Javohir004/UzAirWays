package uz.jvh.uzairways.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.jvh.uzairways.respository.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;



}
