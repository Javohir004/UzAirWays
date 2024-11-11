package uz.jvh.uzairways.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jvh.uzairways.domain.entity.ImportantInfo;

import java.util.UUID;

@Repository
public interface ImportantInfoRepository extends JpaRepository<ImportantInfo, UUID> {

}
