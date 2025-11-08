package yurigetaruck.authentication.modules.auth.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yurigetaruck.authentication.config.TokenConfig;
import yurigetaruck.authentication.modules.auth.user.dto.request.LoginRequest;
import yurigetaruck.authentication.modules.auth.user.dto.request.RegisterUserRequest;
import yurigetaruck.authentication.modules.auth.user.dto.response.LoginResponse;
import yurigetaruck.authentication.modules.auth.user.dto.response.RegisterUserResponse;
import yurigetaruck.authentication.modules.auth.user.model.User;
import yurigetaruck.authentication.modules.auth.user.repository.UserRepository;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;


    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );

    public RegisterUserResponse register(RegisterUserRequest request) {
        validateValidEmail(request.email());
        validateUniqueEmail(request.email());
        var passwordEncoded = passwordEncoder.encode(request.password());

        return RegisterUserResponse.of(repository.save(User.of(request, passwordEncoded)));
    }

    public LoginResponse login(LoginRequest request) {
        var userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var authentication = authenticationManager.authenticate(userAndPass);
        var user = (User) authentication.getPrincipal();
        var token = tokenConfig.generateToken(user);

        return new LoginResponse(request.email(), token);
    }

    private void validateValidEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("E-mail inválido: " + email);
        }
    }

    public void validateUniqueEmail(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email já registrado: " + email);
        }
    }
}
