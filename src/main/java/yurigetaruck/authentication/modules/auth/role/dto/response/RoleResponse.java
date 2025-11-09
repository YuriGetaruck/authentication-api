package yurigetaruck.authentication.modules.auth.role.dto.response;

import yurigetaruck.authentication.modules.auth.role.model.Role;

public record RoleResponse(Integer id, String name, String code) {

    public static RoleResponse of(Role role) {
        return new RoleResponse(role.getId(), role.getName(), role.getCode());
    }
}
