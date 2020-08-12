package ITNews.project.ITNews.service;

import ITNews.project.ITNews.model.TokenAuthEntity;
import ITNews.project.ITNews.repository.TokenAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final TokenAuthRepository tokenAuthRepository;

    public void corruptToken(Optional<TokenAuthEntity> verToken) {
        verToken.get().setEnabled(false);
        tokenAuthRepository.save(verToken.get());
    }
}
