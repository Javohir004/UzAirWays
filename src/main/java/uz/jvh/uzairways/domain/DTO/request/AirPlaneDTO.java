package uz.jvh.uzairways.domain.DTO.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AirPlaneDTO {

    private String model;

    private int capacity;

    private String manufacturer;

    private String aircraftType;  // e.g., "Jet", "Propeller"

}
