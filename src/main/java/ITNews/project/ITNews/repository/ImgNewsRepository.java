package ITNews.project.ITNews.repository;

import ITNews.project.ITNews.model.ImgNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImgNewsRepository extends JpaRepository<ImgNewsEntity, Long> {
    List<ImgNewsEntity> findByIdNewsAndMainImg(Long newsId, Boolean main);
    List<ImgNewsEntity> findByIdNews(Long newsId);
    Optional<ImgNewsEntity> findByCloudIdImg(String cloudIdImg);
    void deleteByCloudIdImg(String cloudIdImg);
}
