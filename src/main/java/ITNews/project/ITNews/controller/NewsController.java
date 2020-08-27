package ITNews.project.ITNews.controller;

import ITNews.project.ITNews.dto.ControllerResponse;
import ITNews.project.ITNews.dto.NewsControllerResponse;
import ITNews.project.ITNews.dto.NewsRequest;
import ITNews.project.ITNews.model.ImgNewsEntity;
import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @PostMapping("/addNews")
    public NewsControllerResponse addNews(@AuthenticationPrincipal UserEntity userData,
                                          @RequestBody NewsRequest newsRequest) {
        return newsService.add(newsRequest, userData );
    }

    @GetMapping("/getAllNews")
    public List<NewsRequest> getAllNews() {
        return newsService.getAll();
    }

    @GetMapping("/getAllImgNews/{newsId}")
    public List<ImgNewsEntity> getAllImgNews(@PathVariable Long newsId){
        return newsService.getAllImg(newsId);
    }

    @GetMapping("/getUsersNews")
    public List<NewsRequest> getUsersNews(@AuthenticationPrincipal UserEntity userData) {
        return newsService.getUsersNews(userData);
    }

    @GetMapping("/getNews/{id}")
    public NewsRequest getNews(@PathVariable String id){
        return newsService.getNews(Long.parseLong(id));
    }

    @PutMapping("/updateNews/{newsId}")
    public ControllerResponse updateNews(@AuthenticationPrincipal UserEntity userData,
                                         @PathVariable Long newsId,
                                         @RequestBody NewsRequest newsRequest) {
        return newsService.update(newsRequest, newsId);
    }

    @DeleteMapping("/deleteNews/{newsId}")
    public ControllerResponse  deleteNews(@AuthenticationPrincipal UserEntity userData,
                                          @PathVariable Long newsId) {
        return newsService.delete(newsId, userData);
    }
}
