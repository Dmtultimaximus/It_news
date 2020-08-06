package ITNews.project.demo.repository;

import ITNews.project.demo.model.TokenAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenAuthRepository extends JpaRepository<TokenAuthEntity, Long> {
    Optional<TokenAuthEntity> findByUserToken(String token);
    Optional<TokenAuthEntity> getByUserId(Long userId);
    Optional<TokenAuthEntity> findByUserIdAndEnabled(Long userId, Boolean enabled);
}
