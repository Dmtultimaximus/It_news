package ITNews.project.ITNews.repository;

import ITNews.project.ITNews.model.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
    Optional<AuthorityEntity> findByRoleId(Long roleId);
    List<AuthorityEntity> findAll();

}
