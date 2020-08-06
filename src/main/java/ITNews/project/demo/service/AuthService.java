package ITNews.project.demo.service;

import ITNews.project.demo.dto.AuthenticationResponse;
import ITNews.project.demo.dto.LoginRequest;
import ITNews.project.demo.dto.RegisterRequest;
import ITNews.project.demo.exeption.MyExeption;
import ITNews.project.demo.exeption.SpringException;
import ITNews.project.demo.model.NotificationEmail;
import ITNews.project.demo.model.TokenAuthEntity;
import ITNews.project.demo.model.UserEntity;
import ITNews.project.demo.model.VerificationTokenEntity;
import ITNews.project.demo.repository.TokenAuthRepository;
import ITNews.project.demo.repository.UserRepository;
import ITNews.project.demo.repository.VerificationTokenRepository;
import ITNews.project.demo.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@EnableAsync
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final JwtProvider jwtProvider;
    private final TokenAuthRepository tokenAuthRepository;
    private final TokenService tokenService;

    public void signup(RegisterRequest registerRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerRequest.getUsername());
        userEntity.setEmail(registerRequest.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userEntity.setCreated(LocalDateTime.now());
        userEntity.setEnabled(false);
        userRepository.save(userEntity);
        String token = generateVerificationToken(userEntity);
        mailService.sendMail(new NotificationEmail("Please activate your accaont",
                userEntity.getEmail(),
                "Thank you for signin " +
                        "http://localhost:8080/api/auth/accountVerification/" + token));
    }
    public String generateVerificationToken(UserEntity userEntity) {
        String token = UUID.randomUUID().toString();
        VerificationTokenEntity verificationTokenEntity = new VerificationTokenEntity();
        verificationTokenEntity.setToken(token);
        verificationTokenEntity.setUserEntity(userEntity);
        verificationTokenRepository.save(verificationTokenEntity);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationTokenEntity> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringException("invalid token"));
        if(verificationToken.get().isEnabled()){
            throw new MyExeption("you already activated this account");
        } else {
            verificationToken.get().setEnabled(true);
            fetchUserAndEnable(verificationToken.get());
        }
    }

    public void fetchUserAndEnable(VerificationTokenEntity verificationTokenEntity) {
        String username = verificationTokenEntity.getUserEntity().getUsername();
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new SpringException("user not found with name - " + username));
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {

        UserEntity userEntity = userRepository.findByUsername(loginRequest.getUsername()).get();
        Optional<TokenAuthEntity> verToken = tokenAuthRepository.findByUserIdAndEnabled(userEntity.getUserId(), true);
        if (verToken.isEmpty()){
            return getMeAuthToken(userEntity, loginRequest);
        } else if (verToken.get().getTime_token().isAfter(LocalDateTime.now())){
            throw new MyExeption("you alredy auth");
        } else {
            tokenService.corruptToken(verToken);
            return getMeAuthToken(userEntity, loginRequest);
        }
    }

    private AuthenticationResponse getMeAuthToken(UserEntity userEntity,LoginRequest loginRequest) {
        String token = jwtProvider.generateToken(userEntity);
        fillTokenAuthAndSave(loginRequest.getUsername(),token);
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }

    public void fillTokenAuthAndSave(String username, String token) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new SpringException("user not found with name - " + username));
        TokenAuthEntity user_token = new TokenAuthEntity();
        user_token.setUserToken(token);
        user_token.setTime_token(LocalDateTime.now().plusDays(1));
        user_token.setUserId(user.getUserId());
        user_token.setEnabled(true);
        tokenAuthRepository.save(user_token);
    }

    public void logout(UserEntity userdata) {
        TokenAuthEntity userToken = tokenAuthRepository.getByUserId(userdata.getUserId()).orElseThrow(() -> new SpringException("user not found with id - " + userdata.getUserId()));
        userToken.setEnabled(false);
        tokenAuthRepository.save(userToken);
    }


}
