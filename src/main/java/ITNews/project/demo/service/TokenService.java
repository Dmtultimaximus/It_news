package ITNews.project.demo.service;

import ITNews.project.demo.model.TokenAuthEntity;
import ITNews.project.demo.repository.TokenAuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class TokenService {

    private final TokenAuthRepository tokenAuthRepository;

    public void corruptToken(Optional<TokenAuthEntity> verToken) {
        verToken.get().setEnabled(false);
        tokenAuthRepository.save(verToken.get());
    }
}
