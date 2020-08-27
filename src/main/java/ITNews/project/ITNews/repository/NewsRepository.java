package ITNews.project.ITNews.repository;

import ITNews.project.ITNews.model.NewsEntity;
import ITNews.project.ITNews.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    List<NewsEntity> findAllByUser(UserEntity userEntity);
    NewsEntity findByNewsId(Long newsId);
    Optional<NewsEntity> findByNewsIdAndUser(Long newsId, UserEntity userEntity);
}
