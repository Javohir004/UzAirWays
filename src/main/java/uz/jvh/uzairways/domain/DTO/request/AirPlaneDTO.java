package uz.jvh.uzairways.domain.DTO.request;

import lombok.*;
import uz.jvh.uzairways.domain.enumerators.AircraftType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AirPlaneDTO {

    private String model;

    private String manufacturer;

    private AircraftType aircraftType;  // e.g., "Jet", "Propeller"

}
