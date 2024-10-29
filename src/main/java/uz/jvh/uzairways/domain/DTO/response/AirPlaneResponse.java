package uz.jvh.uzairways.domain.DTO.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AirPlaneResponse {

    private String model;

    private int capacity;

    private String manufacturer;

    private String aircraftType;  // e.g., "Jet", "Propeller"

}
