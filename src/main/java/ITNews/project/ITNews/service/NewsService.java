package ITNews.project.ITNews.service;

import ITNews.project.ITNews.dto.ControllerResponse;
import ITNews.project.ITNews.dto.NewsControllerResponse;
import ITNews.project.ITNews.dto.NewsRequest;
import ITNews.project.ITNews.model.ImgNewsEntity;
import ITNews.project.ITNews.model.NewsEntity;
import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.repository.ImgNewsRepository;
import ITNews.project.ITNews.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsService {


    private final RatingService ratingService;
    private final NewsRepository newsRepository;
    private final ImgNewsRepository imgNewsRepository;
    private final CloudinaryService cloudinaryService;

    public NewsControllerResponse add(NewsRequest newsRequest, UserEntity userData) {
        return new NewsControllerResponse(newsRepository.save(createNews(newsRequest, userData)).getNewsId(),"Add News", "success", true);
    }

    public List<NewsRequest> getAll() {
        return newsRepository.findAll().stream()
                                        .map(this::mapForNews)
                                        .collect(Collectors.toList());
    }

    public NewsRequest getNews(Long newsId){
        return mapForNews(newsRepository.findByNewsId(newsId));
    }

    private NewsRequest mapForNews(NewsEntity newsEntity) {
        return NewsRequest.builder().rating(ratingService.getRating(newsEntity.getNewsId()))
                                    .userId(newsEntity.getUser().getUserId())
                                    .newsId(newsEntity.getNewsId())
                                    .newsname(newsEntity.getNewsname())
                                    .description(newsEntity.getDescription())
                                    .tags(newsEntity.getTags())
                                    .text(newsEntity.getText())
                                    .urlImg(cloudinaryService.getImgNews(newsEntity.getNewsId()))
                                    .build();
    }

    public ControllerResponse update(NewsRequest newsRequest, Long newsId) {
        NewsEntity news = newsRepository.getOne(newsId);
        news.setNewsname(newsRequest.getNewsname());
        news.setDescription(newsRequest.getDescription());
        news.setTags(newsRequest.getTags());
        news.setText(newsRequest.getText());
        news.setUrlImg(newsRequest.getUrlImg());
        newsRepository.save(news);
        return new ControllerResponse("Update News", "success", true);
    }

    public ControllerResponse delete(Long newsId, UserEntity userData) {
        Optional<NewsEntity> findNews =  newsRepository.findByNewsIdAndUser(newsId, userData);
        if (findNews.isPresent()){
            newsRepository.deleteById(newsId);
            return new  ControllerResponse("Delete news","success", true);
        } else {
            return new  ControllerResponse("Delete news","error", false);
        }
    }

    private NewsEntity createNews(NewsRequest newsRequest, UserEntity userData) {
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setNewsname(newsRequest.getNewsname());
        newsEntity.setDescription(newsRequest.getDescription());
        newsEntity.setTags(newsRequest.getTags());
        newsEntity.setUrlImg(newsRequest.getUrlImg());
        newsEntity.setText(newsRequest.getText());
        newsEntity.setCreatedDate(LocalDateTime.now());
        newsEntity.setUser(userData);
        return newsEntity;
    }


    public List<NewsRequest> getUsersNews(UserEntity userData) {
        return newsRepository.findAllByUser(userData).stream()
                                                     .map(this::mapForNews)
                                                     .collect(Collectors.toList());
    }
}
