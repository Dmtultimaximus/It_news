package ITNews.project.ITNews.service;

import ITNews.project.ITNews.model.CommentEntity;
import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class WebSocketService {
    String messageBody;
    String separator = "$:";
    private final CommentRepository commentRepository;
    public String add(String message, UserEntity userdata, String newsId) {
//        String newsId = (String) ((LinkedMultiValueMap) map.get("nativeHeaders")).get("news").get(0);
        CommentEntity newComment = new CommentEntity();
        newComment.setText(message);
        newComment.setCreatedDate(LocalDateTime.now());
        newComment.setNews(Long.parseLong(newsId));
        newComment.setUser(userdata);
        messageBody = message + separator + userdata.getUsername() + separator + commentRepository.save(newComment).getCommentId();;
        return messageBody;
    }
}
