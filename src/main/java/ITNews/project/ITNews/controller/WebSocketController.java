package ITNews.project.ITNews.controller;

import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final WebSocketService webSocketService;

    @MessageMapping("/send/message/{newsId}")
    @SendTo("/message/{newsId}")
    public String sendMessage(@DestinationVariable String newsId,
                              @AuthenticationPrincipal UserEntity userEntity,
                              String message) {
        return webSocketService.add(message, userEntity, newsId);
    }
}
