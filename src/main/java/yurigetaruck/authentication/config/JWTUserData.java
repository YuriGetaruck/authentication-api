package yurigetaruck.authentication.config;

import java.util.List;

public record JWTUserData (Integer userId, String email, List<String> roles){
}
