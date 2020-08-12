package ITNews.project.ITNews.repository;

import ITNews.project.ITNews.model.TokenAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenAuthRepository extends JpaRepository<TokenAuthEntity, Long> {
    Optional<TokenAuthEntity> findByUserToken(String token);
    Optional<TokenAuthEntity> getByUserToken(String userToken);
}
