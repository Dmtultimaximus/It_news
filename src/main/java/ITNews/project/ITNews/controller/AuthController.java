package ITNews.project.ITNews.controller;

import ITNews.project.ITNews.dto.*;
import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public boolean sightup(@RequestBody @Valid RegisterRequest registerRequest) {
        return authService.signup(registerRequest);
    }

    @GetMapping("/get-details-user")
    public UserDetailsResponse getDetails(@AuthenticationPrincipal UserEntity userData) {
        return authService.getDataOfUser(userData);
    }

    @GetMapping("account-verification/{token}")
    public boolean verifyAccount(@PathVariable String token) {
        return authService.verifyAccount(token);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/logout")
    public LogoutRequest logout(@AuthenticationPrincipal UserEntity userdata,
                                @RequestBody String token) {
        return authService.logout(userdata, token);
    }
}

