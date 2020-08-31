package ITNews.project.ITNews.repository;

import ITNews.project.ITNews.model.UserRaitingNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<UserRaitingNewsEntity, Long> {
    Optional<UserRaitingNewsEntity> findByUserIdAndNewsId(Long userId, Long newsId);
    List<UserRaitingNewsEntity> findAllByNewsId(Long newsId);
}
