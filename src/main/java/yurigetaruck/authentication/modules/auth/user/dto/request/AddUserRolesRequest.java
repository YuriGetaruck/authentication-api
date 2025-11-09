package yurigetaruck.authentication.modules.auth.user.dto.request;

import java.util.List;

public record AddUserRolesRequest(List<String> roles) {
}
