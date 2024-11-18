package uz.jvh.uzairways.domain.DTO.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AirPlaneResponse {

    @JsonProperty("id")
    private UUID id;

    private String model;

    private String manufacturer;

    private String aircraftType;  // e.g., "Jet", "Propeller"

}
