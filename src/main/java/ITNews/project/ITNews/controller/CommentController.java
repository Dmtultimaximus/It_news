package ITNews.project.ITNews.controller;

import ITNews.project.ITNews.dto.CommentRequest;
import ITNews.project.ITNews.dto.ControllerResponse;
import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/addComment")
    public ControllerResponse addComment(@AuthenticationPrincipal UserEntity userdata,
                                         @RequestParam Long newsId,
                                         @RequestBody CommentRequest commentRequest) {
        return commentService.add(commentRequest, userdata, newsId);
    }

    @GetMapping
    public List<CommentRequest> getAllComments(@RequestParam Long newsId) {
        return commentService.getAll(newsId);
    }

    @PostMapping("/addLike")
    public ControllerResponse addlike(@AuthenticationPrincipal UserEntity userData,
                                          @RequestParam Long commentId) {
        return commentService.addLike(userData, commentId);
    }
}
