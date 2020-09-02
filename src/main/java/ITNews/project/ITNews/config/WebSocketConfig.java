package ITNews.project.ITNews.config;

import ITNews.project.ITNews.model.TokenAuthEntity;
import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.repository.TokenAuthRepository;
import ITNews.project.ITNews.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Collections;
import java.util.Optional;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final UserRepository userRepository;
    private final TokenAuthRepository tokenAuthRepository;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/socket/**")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker("/message");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                StompCommand command = accessor.getCommand();
                if (command == StompCommand.CONNECT || command == StompCommand.SEND) {
                    String token = accessor.getNativeHeader("Authorization").stream().findFirst().orElse(null);
                    Optional<TokenAuthEntity> userToken = tokenAuthRepository.findByUserToken(token);

                    UserEntity user = userRepository.findByUserId(userToken.get().getUserId()).orElseThrow(() -> new UsernameNotFoundException("user not found with name"));
                    Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                    accessor.setUser(auth);
                }
                return message;
            }
        });

    }
}
