package yurigetaruck.authentication.modules.auth.user.dto.response;

import java.util.List;

public record AddUserRolesResponse(List<String> roles, Integer userId) {
}
