package uz.jvh.uzairways.domain.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
//@Table(name = "airplanes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AirPlane extends BaseEntity {

    private String model;

    private int capacity;

    private String manufacturer;

    private String aircraftType;  // e.g., "Jet", "Propeller"

}
