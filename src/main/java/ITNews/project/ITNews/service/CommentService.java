package ITNews.project.ITNews.service;

import ITNews.project.ITNews.dto.CommentRequest;
import ITNews.project.ITNews.dto.ControllerResponse;
import ITNews.project.ITNews.exeption.CommentLikeException;
import ITNews.project.ITNews.model.CommentEntity;
import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.model.UserLikeCommentEntity;
import ITNews.project.ITNews.repository.CommentRepository;
import ITNews.project.ITNews.repository.LikeRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public ControllerResponse add(CommentRequest commentRequest, UserEntity userdata, Long newsId) {
        CommentEntity newComment = new CommentEntity();
        newComment.setText(commentRequest.getText());
        newComment.setCreatedDate(LocalDateTime.now());
        newComment.setNews(newsId);
        newComment.setUser(userdata);
        commentRepository.save(newComment);
        return new ControllerResponse("Add Comment", "success", true);
    }

    public List<CommentRequest> getAll(Long newsId) {
        return commentRepository.findAllByNews(newsId).stream()
                .map(this::mapForComments)
                .collect(Collectors.toList());
    }

    public CommentRequest mapForComments(CommentEntity commentEntity) {
        return CommentRequest.builder().user(commentEntity.getUser().getUsername())
                .likes(commentEntity.getLikes())
                .date(commentEntity.getCreatedDate())
                .text(commentEntity.getText())
                .build();
    }

    public ControllerResponse addLike(UserEntity userData, Long commentId) {
        Optional<UserLikeCommentEntity> userLike = likeRepository.findByUserId(userData.getUserId());
        if (userLike.isPresent()) {
            throw new CommentLikeException("you already added like");
        } else {
            UserLikeCommentEntity like = new UserLikeCommentEntity();
            like.setCommentId(commentId);
            like.setUserId(userData.getUserId());
            likeRepository.save(like);
            CommentEntity updatelike = commentRepository.getOne(commentId);
            updatelike.setLikes(likeRepository.countByCommentId(commentId));
            commentRepository.save(updatelike);
        }
        return new ControllerResponse("Add like", "success", true);
    }
}
