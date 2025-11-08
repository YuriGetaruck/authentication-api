package yurigetaruck.authentication.modules.auth.user.dto.response;

import yurigetaruck.authentication.modules.auth.user.model.User;

public record RegisterUserResponse (String email, String name){

    public static RegisterUserResponse of(User user) {
        return new RegisterUserResponse(user.getEmail(), user.getName());
    }
}
