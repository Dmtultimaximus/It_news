package ITNews.project.ITNews.service;

import ITNews.project.ITNews.dto.CheckLikeResponse;
import ITNews.project.ITNews.dto.CommentRequest;
import ITNews.project.ITNews.dto.ControllerResponse;
import ITNews.project.ITNews.model.CommentEntity;
import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.model.UserLikeCommentEntity;
import ITNews.project.ITNews.repository.CommentRepository;
import ITNews.project.ITNews.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        return CommentRequest.builder().commentId(commentEntity.getCommentId())
                .user(commentEntity.getUser().getUsername())
                .likes(commentEntity.getLikes())
                .date(commentEntity.getCreatedDate())
                .text(commentEntity.getText())
                .build();
    }

    public boolean addLike(UserEntity userData, Long commentId) {
        Optional<UserLikeCommentEntity> userLike = likeRepository.findByUserIdAndCommentId(userData.getUserId(), commentId);
        if (userLike.isPresent()) {
            return false;
        } else {
            UserLikeCommentEntity like = new UserLikeCommentEntity();
            like.setCommentId(commentId);
            like.setUserId(userData.getUserId());
            likeRepository.save(like);
            CommentEntity updatelike = commentRepository.getOne(commentId);
            updatelike.setLikes(likeRepository.countByCommentId(commentId));
            commentRepository.save(updatelike);
        }
        return true;
    }

    public List checkLike(UserEntity userData, Long newsId) {
        List<CheckLikeResponse> checkLikeResponseList = new ArrayList<CheckLikeResponse>();
        List<CommentEntity> comments = commentRepository.findAllByNews(newsId);
        comments.forEach(commentEntity -> {
            Optional<UserLikeCommentEntity> userLike = likeRepository.findByUserIdAndCommentId(userData.getUserId(), commentEntity.getCommentId());
            CheckLikeResponse checkLikeResponse = new CheckLikeResponse();
            checkLikeResponse.setUserId(userData.getUserId());
            checkLikeResponse.setCheck(userLike.isEmpty());
            checkLikeResponseList.add(checkLikeResponse);
        });
        return checkLikeResponseList;
    }
}
