package ITNews.project.ITNews.controller;

import ITNews.project.ITNews.dto.ControllerResponse;
import ITNews.project.ITNews.dto.NewsControllerResponse;
import ITNews.project.ITNews.dto.NewsRequest;
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

    @GetMapping
    public List<NewsRequest> getAllNews() {
        return newsService.getAll();
    }

    @GetMapping("/profile/user")
    public List<NewsRequest> getUsersNews(@AuthenticationPrincipal UserEntity userData) {
        return newsService.getUsersNews(userData);
    }

    @PostMapping("/")
    public NewsControllerResponse addNews(@AuthenticationPrincipal UserEntity userData,
                                          @RequestBody NewsRequest newsRequest) {
        return newsService.add(newsRequest, userData);
    }

    @GetMapping("/{id}")
    public NewsRequest getNews(@PathVariable String id) {
        return newsService.getNews(Long.parseLong(id));
    }

    @PutMapping("/{id}")
    public boolean updateNews(@PathVariable Long id,
                              @RequestBody NewsRequest newsRequest) {
        return newsService.update(newsRequest, id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteNews(@AuthenticationPrincipal UserEntity userData,
                              @PathVariable Long id) {
        return newsService.delete(id, userData);
    }
}
