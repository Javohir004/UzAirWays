package uz.jvh.uzairways.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jvh.uzairways.domain.entity.AboutUs;

import java.util.List;
import java.util.UUID;

@Repository
public interface AboutUsEntityRepo extends JpaRepository<AboutUs , UUID> {

    List<AboutUs> getByAndIsActiveTrue();
}
