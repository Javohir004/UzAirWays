package uz.jvh.uzairways.respository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jvh.uzairways.domain.entity.AirPlane;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AirPlaneRepository extends JpaRepository<AirPlane, UUID> {

    Optional<AirPlane> findByIdAndIsActiveTrue(UUID id);

    List<AirPlane> findAllByIsActiveTrueOrderByCreatedDesc();
}
