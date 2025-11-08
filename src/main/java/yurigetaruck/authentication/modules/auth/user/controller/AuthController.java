package yurigetaruck.authentication.modules.auth.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yurigetaruck.authentication.modules.auth.user.dto.request.LoginRequest;
import yurigetaruck.authentication.modules.auth.user.dto.request.RegisterUserRequest;
import yurigetaruck.authentication.modules.auth.user.dto.response.LoginResponse;
import yurigetaruck.authentication.modules.auth.user.dto.response.RegisterUserResponse;
import yurigetaruck.authentication.modules.auth.user.service.UserService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login com email: {}", loginRequest.email());
        var login = service.login(loginRequest);
        log.info("Login realizado com sucesso para: {}", loginRequest.email());

        return ResponseEntity.status(HttpStatus.OK).body(login);
    }

    @PostMapping("register")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        log.info("Cadastro usuário: {}", registerUserRequest.email());
        var registeredUser = service.register(registerUserRequest);
        log.info("Usuário {} cadastrado com sucesso.", registeredUser.email());

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
}
