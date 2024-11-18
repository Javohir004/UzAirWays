package uz.jvh.uzairways.domain.DTO.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AirPlaneResponse {

    private UUID id;

    private String model;

    private String manufacturer;

    private String aircraftType;  // e.g., "Jet", "Propeller"

}
