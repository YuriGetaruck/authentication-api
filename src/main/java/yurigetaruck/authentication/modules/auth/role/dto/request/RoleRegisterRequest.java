package yurigetaruck.authentication.modules.auth.role.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record RoleRegisterRequest(@NotEmpty(message = "Name inválido.") String name,
                                  @NotEmpty(message = "Application inválido") String application) {
}
