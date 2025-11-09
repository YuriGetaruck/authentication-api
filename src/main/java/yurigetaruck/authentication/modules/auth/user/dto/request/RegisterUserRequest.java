package yurigetaruck.authentication.modules.auth.user.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record RegisterUserRequest(@NotEmpty(message = "name is mandatory!") String name,
                                  @NotEmpty(message = "email is mandatory!") String email,
                                  @NotEmpty(message = "password is mandatory!") String password,
                                  List<String> roles) {
}
