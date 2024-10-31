package uz.jvh.uzairways.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jvh.uzairways.Views.UserView;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.domain.enumerators.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Boolean existsByUsernameAndPassword(String username, String password);

    Boolean existsByEnabledTrueAndUsernameOrEmail(String username, String email);

    List<UserView> findByRoleAndIsActiveTrue(UserRole role);

    Optional<User> findByUsername(String username);


}
