package uz.jvh.uzairways.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.jvh.uzairways.domain.entity.User;
import uz.jvh.uzairways.domain.enumerators.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByRoleAndIsActiveTrue(UserRole role);

    Optional<User> findByUsername(String username);

    List<User>findAllByIsActiveTrue();


    @Query("SELECT u FROM User u WHERE LOWER(TRIM(u.email)) = LOWER(TRIM(:email))")
    Optional<User> findByEmail(@Param("email") String email);



    Optional<User> findByVerificationToken(String token);
}
