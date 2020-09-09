package ITNews.project.ITNews.service;

import ITNews.project.ITNews.dto.*;
import ITNews.project.ITNews.exeption.TokenEmailException;
import ITNews.project.ITNews.model.NotificationEmail;
import ITNews.project.ITNews.model.TokenAuthEntity;
import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.model.VerificationTokenEntity;
import ITNews.project.ITNews.repository.TokenAuthRepository;
import ITNews.project.ITNews.repository.UserRepository;
import ITNews.project.ITNews.repository.VerificationTokenRepository;
import ITNews.project.ITNews.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    static final String address = "http://localhost:8080/api/auth/account-verification/";

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final JwtProvider jwtProvider;
    private final TokenAuthRepository tokenAuthRepository;
    private final AuthenticationManager authenticationManager;

    public boolean signup(RegisterRequest registerRequest) {
        Optional<UserEntity> userRepeat = userRepository.findFirstByUsernameOrEmail(registerRequest.getUsername(), registerRequest.getEmail());
        if (userRepeat.isPresent() && (userRepeat.get().getEmail().equals(registerRequest.getEmail()))) {
            return false;
        } else if (userRepeat.isPresent() && (userRepeat.get().getUsername().equals(registerRequest.getUsername()))) {
            return false;
        } else {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(registerRequest.getUsername());
            userEntity.setEmail(registerRequest.getEmail());
            userEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            userEntity.setCreated(LocalDateTime.now());
            userEntity.setEnabled(false);
            userRepository.save(userEntity);
            String token = generateVerificationToken(userEntity);
            mailService.sendMail(new NotificationEmail("Please activate your accant",
                    userEntity.getEmail(),
                    "Thank you for signin " + address + token));
            return true;
        }
    }

    public String generateVerificationToken(UserEntity userEntity) {
        String token = UUID.randomUUID().toString();
        VerificationTokenEntity verificationTokenEntity = new VerificationTokenEntity();
        verificationTokenEntity.setToken(token);
        verificationTokenEntity.setUserEntity(userEntity);
        verificationTokenRepository.save(verificationTokenEntity);
        return token;
    }

    public boolean verifyAccount(String token) {
        Optional<VerificationTokenEntity> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new TokenEmailException("invalid token"));
        if (verificationToken.get().isEnabled()) {
            throw new TokenEmailException("you already activated this account");
        } else {
            verificationToken.get().setEnabled(true);
            fetchUserAndEnable(verificationToken.get());
            return true;
        }
    }

    public void fetchUserAndEnable(VerificationTokenEntity verificationTokenEntity) {
        String username = verificationTokenEntity.getUserEntity().getUsername();
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new TokenEmailException("user not found with name - " + username));
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        return getMeAuthToken((UserEntity) authentication.getPrincipal(), loginRequest);
    }

    private AuthenticationResponse getMeAuthToken(UserEntity userEntity, LoginRequest loginRequest) {
        String token = jwtProvider.generateToken(userEntity);
        fillTokenAuthAndSave(loginRequest.getUsername(), token);
        return new AuthenticationResponse(userEntity.getUserId(), token, loginRequest.getUsername(), true);
    }

    public void fillTokenAuthAndSave(String username, String token) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found with name - " + username));
        TokenAuthEntity user_token = new TokenAuthEntity();
        user_token.setUserToken(token);
        user_token.setTime_token(LocalDateTime.now().plusDays(1));
        user_token.setUserId(user.getUserId());
        user_token.setEnabled(true);
        tokenAuthRepository.save(user_token);
    }

    public LogoutRequest logout(UserEntity userdata, String token) {
        TokenAuthEntity userToken = tokenAuthRepository.getByUserToken(token).orElseThrow(() -> new UsernameNotFoundException("user not found with id - " + userdata.getUserId()));
        userToken.setEnabled(false);
        tokenAuthRepository.save(userToken);
        return new LogoutRequest(true);
    }

    public UserDetailsResponse getDataOfUser(UserEntity userData) {
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setUsername(userData.getUsername());
        userDetailsResponse.setEmail(userData.getEmail());
        userDetailsResponse.setCreateDate(userData.getCreated());
        userDetailsResponse.setRole(userData.getAuthorities());
        return userDetailsResponse;
    }
}
