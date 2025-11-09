package yurigetaruck.authentication.modules.auth.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import yurigetaruck.authentication.config.JWTUserData;
import yurigetaruck.authentication.config.TokenConfig;
import yurigetaruck.authentication.modules.auth.common.exception.ValidationException;
import yurigetaruck.authentication.modules.auth.role.model.Role;
import yurigetaruck.authentication.modules.auth.role.service.RoleService;
import yurigetaruck.authentication.modules.auth.user.dto.request.AddUserRolesRequest;
import yurigetaruck.authentication.modules.auth.user.dto.request.LoginRequest;
import yurigetaruck.authentication.modules.auth.user.dto.request.RegisterUserRequest;
import yurigetaruck.authentication.modules.auth.user.dto.response.AddUserRolesResponse;
import yurigetaruck.authentication.modules.auth.user.dto.response.LoginResponse;
import yurigetaruck.authentication.modules.auth.user.dto.response.RegisterUserResponse;
import yurigetaruck.authentication.modules.auth.user.model.User;
import yurigetaruck.authentication.modules.auth.user.repository.UserRepository;

import java.util.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RoleService roleService;
    private final TokenConfig tokenConfig;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );

    public RegisterUserResponse register(RegisterUserRequest request) {
        validateEmailPattern(request.email());
        validateUniqueEmail(request.email());
        var roles = validateRoles(request.roles());
        var passwordEncoded = passwordEncoder.encode(request.password());

        return RegisterUserResponse.of(repository.save(User.of(request, passwordEncoded, roles)));
    }

    public LoginResponse login(LoginRequest request) {
        var userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var authentication = authenticationManager.authenticate(userAndPass);
        var user = (User) authentication.getPrincipal();
        var token = tokenConfig.generateToken(user);

        return new LoginResponse(request.email(), token);
    }

    public AddUserRolesResponse addUserRoles(AddUserRolesRequest addUserRoles) {
        var roles = roleService.getRolesFromNames(addUserRoles.roles());
        var user = getAuthenticatedUser();
        var userRoles = user.getRoles();
        userRoles.addAll(roles);
        user.setRoles(new HashSet<>(userRoles));

        repository.save(user);

        return new AddUserRolesResponse(userRoles.stream().map(Role::getName).toList(), user.getId());
    }

    private User getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof JWTUserData principal) {

            return repository.findById(principal.userId())
                    .orElseThrow(() -> new ValidationException("Usuario não encontrado."));
        }

        return new User(null, authentication.getName(), null, null, Collections.emptySet());
    }

    private void validateEmailPattern(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("E-mail inválido: " + email);
        }
    }

    private void validateUniqueEmail(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email já registrado: " + email);
        }
    }

    private Set<Role> validateRoles(List<String> roles) {
        var rolesValidated = roleService.getRolesFromNames(roles);

        if (rolesValidated.stream().map(Role::getCode).toList().contains("ROLE_ADMIN")) {
            var user = getAuthenticatedUser();
            if (!user.isAdmin()) {
                throw new ValidationException("Usuário não pode cadastrar a ADMIN.");
            }
        }

        return rolesValidated;
    }
}
