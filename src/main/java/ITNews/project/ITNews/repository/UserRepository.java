package ITNews.project.ITNews.repository;

import ITNews.project.ITNews.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByUserId(Long userId);
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);
}
