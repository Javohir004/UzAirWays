package uz.jvh.uzairways.domain.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateBookingRequest {
    private List<UUID> ticketIds;
    private List<EmployeeRequest> employees;
}
