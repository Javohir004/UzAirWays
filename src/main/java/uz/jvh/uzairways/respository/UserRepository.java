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

//    List<User> findByRoleAndIsActiveTrue(UserRole role);

    /** tartib bo'yicha chiqadi **/
    List<User> findByRoleAndIsActiveTrueOrderByCreatedDesc(UserRole role);


    Optional<User> findByUsernameAndIsActiveTrue(String username);

//    List<User>findAllByIsActiveTrue();

    /** tartib bo'yicha chiqadi **/
    List<User> findAllByIsActiveTrueOrderByCreatedDesc();


    Optional<User> findUserByRoleAndIsActiveTrue(UserRole role);
}
