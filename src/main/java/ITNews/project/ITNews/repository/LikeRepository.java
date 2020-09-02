package ITNews.project.ITNews.repository;

import ITNews.project.ITNews.model.UserLikeCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<UserLikeCommentEntity, Long> {
    Integer countByCommentId(Long Id);
    Optional<UserLikeCommentEntity> findByUserId(Long userId);
}
