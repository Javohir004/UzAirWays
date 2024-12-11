package uz.jvh.uzairways.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.jvh.uzairways.domain.entity.Booking;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

//    List<Booking> findByUserIdIsActiveTrue(UUID userId);

//    List<Booking> findByUserIdAndIsActiveTrue(UUID userId);

    List<Booking> findByUserIdAndIsActiveTrueOrderByCreatedDesc(UUID userId);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.isActive = true")
    List<Booking> findActiveBookingsByUserId(@Param("userId") UUID userId);


}
