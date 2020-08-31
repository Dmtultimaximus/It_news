package ITNews.project.ITNews.service;

import ITNews.project.ITNews.dto.ControllerResponse;
import ITNews.project.ITNews.model.CommentEntity;
import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class WebSocketService {

    private final CommentRepository commentRepository;

    public void add(String message, UserEntity userdata, Map map) {
//        Object list = map.get("nativeHeaders");
        String newsId = (String) ((LinkedMultiValueMap) map.get("nativeHeaders")).get("news").get(0);
        CommentEntity newComment = new CommentEntity();
        newComment.setText(message);
        newComment.setCreatedDate(LocalDateTime.now());
        newComment.setNews(Long.parseLong(newsId));
        newComment.setUser(userdata);
        commentRepository.save(newComment);
    }
}
