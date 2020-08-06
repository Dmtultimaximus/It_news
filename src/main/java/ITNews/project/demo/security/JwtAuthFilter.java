package ITNews.project.demo.security;

import ITNews.project.demo.dto.ErrorApi;
import ITNews.project.demo.exeption.MyExeption;
import ITNews.project.demo.exeption.SpringException;
import ITNews.project.demo.model.TokenAuthEntity;
import ITNews.project.demo.model.UserEntity;
import ITNews.project.demo.repository.TokenAuthRepository;
import ITNews.project.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;


public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final TokenAuthRepository tokenAuthRepository;
    private final ObjectMapper objectMapper;

    public JwtAuthFilter(UserRepository userRepository,
                         TokenAuthRepository tokenAuthRepository,
                         ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.tokenAuthRepository = tokenAuthRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt =  getJwtFromRequest(request);
        //код для получения userDetails
        Optional<TokenAuthEntity> userId = tokenAuthRepository.findByUserToken(jwt);
        if (userId.isEmpty()){
            //error
            ErrorApi errorApi = new ErrorApi();
            errorApi.setCodeError(HttpStatus.UNAUTHORIZED.value());
            errorApi.setTimeError(LocalDateTime.now());
            errorApi.setMessage("Invalid token");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(objectMapper.writeValueAsString(errorApi));
            response.getWriter().flush();

        } else {
            UserEntity user = userRepository.findByUserId(userId.get().getUserId()).orElseThrow(() -> new SpringException("user not found with name"));
            //generate AuthenticationPrincipal - data
            Authentication auth =  new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);
            // generate out of filter
            LocalDateTime time = userId.get().getTime_token();
            if( time.isAfter(LocalDateTime.now()) ){
                filterChain.doFilter(request, response);
            } else {
               corruptToken(user);
               //
            }
        }
    }

    @Transactional
    public void corruptToken(UserEntity user) {
        TokenAuthEntity userToken = tokenAuthRepository.getByUserId(user.getUserId()).orElseThrow(() -> new MyExeption("user not found with id - " + user.getUserId()));
        userToken.setEnabled(false);
        tokenAuthRepository.save(userToken);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken =  request.getHeader("authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}
