package ITNews.project.ITNews.controller;

import ITNews.project.ITNews.dto.CheckLikeResponse;
import ITNews.project.ITNews.dto.CommentRequest;
import ITNews.project.ITNews.dto.ControllerResponse;
import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/chek-like")
    public List check(@AuthenticationPrincipal UserEntity userData,
                      @RequestParam Long newsId) {
        return commentService.checkLike(userData, newsId);
    }
}
