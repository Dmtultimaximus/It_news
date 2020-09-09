package ITNews.project.ITNews.controller;

import ITNews.project.ITNews.dto.CommentRequest;
import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentRequest> getAllComments(@RequestParam Long newsId) {
        return commentService.getAll(newsId);
    }

    @GetMapping("/add-like")
    public boolean addlike(@AuthenticationPrincipal UserEntity userData,
                           @RequestParam Long commentId) {
        return commentService.addLike(userData, commentId);
    }

    @GetMapping("/chek-like")
    public List check(@AuthenticationPrincipal UserEntity userData,
                      @RequestParam Long newsId) {
        return commentService.checkLike(userData, newsId);
    }
}
