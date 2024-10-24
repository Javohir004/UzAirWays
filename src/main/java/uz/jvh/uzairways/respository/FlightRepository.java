package uz.jvh.uzairways.respository;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.stereotype.Repository;
import uz.jvh.uzairways.domain.entity.Flight;

import java.util.UUID;

@Repository
public interface FlightRepository extends JpaAttributeConverter<Flight, UUID> {

}
