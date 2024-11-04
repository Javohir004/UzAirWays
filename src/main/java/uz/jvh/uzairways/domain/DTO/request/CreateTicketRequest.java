package uz.jvh.uzairways.domain.DTO.request;

import lombok.*;
import uz.jvh.uzairways.domain.enumerators.AircraftType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateTicketRequest {
    private TicketDTO ticket;
    private AircraftType aircraftType;
}
