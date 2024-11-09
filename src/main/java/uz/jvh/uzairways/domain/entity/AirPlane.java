package uz.jvh.uzairways.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.jvh.uzairways.domain.enumerators.AircraftType;

@Entity
@Table(name = "airplanes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AirPlane extends BaseEntity {

    private String model;

    private String manufacturer;

    @Enumerated(EnumType.STRING)
    private AircraftType aircraftType;  // e.g., "Jet", "Propeller"

}
