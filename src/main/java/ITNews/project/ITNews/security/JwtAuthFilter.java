package ITNews.project.ITNews.security;

import ITNews.project.ITNews.dto.ErrorApi;
import ITNews.project.ITNews.model.TokenAuthEntity;
import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.repository.TokenAuthRepository;
import ITNews.project.ITNews.repository.UserRepository;
import ITNews.project.ITNews.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final TokenService tokenService;

    public JwtAuthFilter(UserRepository userRepository,
                         TokenAuthRepository tokenAuthRepository,
                         ObjectMapper objectMapper,
                         TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenAuthRepository = tokenAuthRepository;
        this.objectMapper = objectMapper;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt =  getJwtFromRequest(request);
        //код для получения userDetails
        Optional<TokenAuthEntity> userToken = tokenAuthRepository.findByUserToken(jwt);
        if (userToken.isEmpty()){
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
            UserEntity user = userRepository.findByUserId(userToken.get().getUserId()).orElseThrow(() -> new UsernameNotFoundException("user not found with name"));
            //generate AuthenticationPrincipal - data
            Authentication auth =  new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(auth);
            // generate out of filter
            LocalDateTime time = userToken.get().getTime_token();
            if( time.isAfter(LocalDateTime.now()) ){
                filterChain.doFilter(request, response);
            } else {

                ErrorApi errorApi = new ErrorApi();
                errorApi.setCodeError(HttpStatus.UNAUTHORIZED.value());
                errorApi.setTimeError(LocalDateTime.now());
                errorApi.setMessage("Life Time");

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(objectMapper.writeValueAsString(errorApi));
                response.getWriter().flush();

                tokenService.corruptToken(userToken);

            }
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken =  request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}
