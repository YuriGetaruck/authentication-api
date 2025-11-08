package yurigetaruck.authentication.modules.auth.user.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(@NotEmpty(message = "e-mail is mandatory! / e-mail é obrigatório!") String email,
                           @NotEmpty(message = "password is mandatory! / password é obrigatório!") String password) {
}
